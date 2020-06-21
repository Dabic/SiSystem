package com.se.source.broker.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.se.source.auth.domain.AppUser;
import com.se.source.auth.domain.Role;
import com.se.source.auth.repositories.IRoleRepository;
import com.se.source.auth.repositories.IUserRepository;
import com.se.source.broker.domain.*;
import com.se.source.broker.dtos.SecurityPolicyDTO;
import com.se.source.broker.implementation.model.Broker;
import com.se.source.broker.implementation.model.context.Context;
import com.se.source.broker.implementation.model.services.interfaces.IHttpService;
import com.se.source.broker.repositories.IPolicyTypeRepository;
import com.se.source.broker.repositories.IProviderRepository;
import com.se.source.broker.repositories.IServiceRepository;
import com.se.source.broker.utils.BrokerConstants;
import com.se.source.shared.ErrorMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.*;

@Controller
@RequestMapping(BrokerConstants.ADMINISTRATION_URL)
public class AdministrationController {

    private final IRoleRepository _roleRepository;
    private final IUserRepository _userRepository;
    private final IProviderRepository _providerRepository;
    private final IServiceRepository _serviceRepository;
    private final IPolicyTypeRepository _policyTypeRepository;
    private final BCryptPasswordEncoder _bCryptPasswordEncoder;
    private final IHttpService _httpService;
    private final Broker _broker;
    private List<SecurityPolicyDTO> securityPolicyDTOList;
    Logger logger = LoggerFactory.getLogger(AdministrationController.class);

    public AdministrationController(
            IRoleRepository roleRepository,
            IUserRepository userRepository,
            IProviderRepository providerRepository,
            IServiceRepository serviceRepository,
            IPolicyTypeRepository policyTypeRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            Broker broker,
            IHttpService httpService) {

        _roleRepository = roleRepository;
        _userRepository = userRepository;
        _providerRepository = providerRepository;
        _serviceRepository = serviceRepository;
        _policyTypeRepository = policyTypeRepository;
        _bCryptPasswordEncoder = bCryptPasswordEncoder;
        _broker = broker;
        securityPolicyDTOList = new ArrayList<>();
        _httpService = httpService;
    }

    @PreAuthorize("hasAuthority('ROLE_SUPERUSER')")
    @RequestMapping(method = RequestMethod.POST, value = "/register-provider")
    public ResponseEntity<String> registerProvider(@RequestBody Provider provider) throws ErrorMessageException {
        AppUser user = _userRepository.findByUsername(provider.getUsername());
        if (user != null)
            throw new ErrorMessageException(
                    String.format("User %s already exists!", provider.getUsername()),
                    HttpStatus.FORBIDDEN
            );
        Optional<Role> roleCheck = _roleRepository.findByName("PROVIDER");
        Optional<Provider> providerCheck = _providerRepository.findByUsername(provider.getUsername());
        if (providerCheck.isPresent()) {
            throw new ErrorMessageException(
                    String.format("Provider %s already exists!", provider.getUsername()),
                    HttpStatus.FORBIDDEN
            );
        }
        if (roleCheck.isPresent()) {
            provider.setPassword(_bCryptPasswordEncoder.encode(provider.getPassword()));
            provider.setRoles(Collections.singletonList(roleCheck.get()));
            _providerRepository.save(provider);
        } else {
            throw new ErrorMessageException("You cannot register as service provider!", HttpStatus.FORBIDDEN);
        }
        //_broker.registerServices();
        return new ResponseEntity<>("Provider registered successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PROVIDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/register-services")
    public ResponseEntity<String> registerServices(@RequestHeader String authorization, @RequestBody List<Service> services) {
        logger.error(services.toString());
        DecodedJWT decodedToken = JWT.decode(authorization.split(" ")[1]);
        Optional<Provider> providerCheck = _providerRepository.findByUsername(decodedToken.getSubject());
        if (providerCheck.isPresent()) {
            Provider provider = providerCheck.get();
            securityPolicyDTOList.clear();
            for (Service service : services) {
                service.setProvider(provider);
                if (service.isComposite()) {
                    for (Service comp : service.getServices()) {
                        comp.setProvider(provider);
                        insideService(comp);
                    }
                }
                insideService(service);

                //nakon registracije servisa NA BROKERA trebalo bi da se posalje eksternom servisu:
                //url(endpointovi,servsi - ono na sta saljemo zahteve)
                //filteri za te urlove, za te i te rolove

                //BROKER bi trebalo da obezbedi da filteri za te urlove zaista postoje kao metasemaatributi

                //kada stigne rispons od nekog servisa(nakon sto je izvrsena njegova registracija)
                //tipa /read ili /create, onda se sekjuriti servisu salje: Role ulogovanog, ceo odgovor(json recimo), url sa kojeg je odgovor stigao
                try {
                    _serviceRepository.save(service);
                    if (!service.getProvider().getUrl().split("\\.")[0].equals(BrokerConstants.SECURITY_FILTER_URL)) {
                        logger.error("Sending data to filter service...");
                        String response = _httpService.getRestTemplate().postForObject("http://localhost:8083/security-filter/save/",
                                securityPolicyDTOList, String.class);
                        securityPolicyDTOList.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
                }
            }
        }
        //_broker.registerServices();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PROVIDER')")
    @RequestMapping("/remove-service")
    public ResponseEntity<String> removeService(@RequestBody Service service) throws ErrorMessageException {
        try {
            _serviceRepository.deleteById(service.getName());
        } catch (EmptyResultDataAccessException e) {
            throw new ErrorMessageException(String.format("Service with name: %s does not exist!", service.getName()),
                    HttpStatus.FORBIDDEN);
        }
        //_broker.registerServices();
        return new ResponseEntity<>("Service successfully removed!", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PROVIDER')")
    @RequestMapping("/update-service")
    public ResponseEntity<String> updateService(@RequestBody Service service) throws ErrorMessageException {
        Optional<Service> oldService = _serviceRepository.findByName(service.getName());
        if (!oldService.isPresent()) {
            throw new ErrorMessageException(String.format("Service with name: %s does not exist!", service.getName()),
                    HttpStatus.FORBIDDEN);
        }
        try {
            _serviceRepository.save(service);
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException e) {
            throw new ErrorMessageException("Could not update service!", HttpStatus.FORBIDDEN);
        }
        //_broker.registerServices();
        return new ResponseEntity<>("Service successfully updated!", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPERUSER')")
    @RequestMapping(method = RequestMethod.POST, value = "/register-user")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) throws ErrorMessageException {
        AppUser oldUser = _userRepository.findByUsername(user.getUsername());
        if (oldUser != null) {
            throw new ErrorMessageException(String.format("User with username: %s already exists!", user.getUsername()),
                    HttpStatus.FORBIDDEN);
        } else {
            for (Role role : user.getRoles()) {
                Optional<Role> oldRole = _roleRepository.findByIdAndName(role.getId(), role.getName());
                if (!oldRole.isPresent()) {
                    throw new ErrorMessageException(String.format("Could not register as role %s with id %d!", role.getName(), role.getId()),
                            HttpStatus.FORBIDDEN);
                }
            }
            user.setPassword(_bCryptPasswordEncoder.encode(user.getPassword()));
            _userRepository.save(user);
        }
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPERUSER')")
    @RequestMapping(method = RequestMethod.POST, value = "/remove-user")
    public ResponseEntity<String> removeUser(@RequestBody AppUser user) throws ErrorMessageException {
        try {
            _userRepository.deleteById(user.getUsername());
        } catch (EmptyResultDataAccessException e) {
            throw new ErrorMessageException(String.format("User with username: %s does not exist!", user.getUsername()),
                    HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(String.format("User: %s removed successfully!", user.getUsername()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPERUSER')")
    @RequestMapping(method = RequestMethod.POST, value = "/update-user")
    public ResponseEntity<String> updateUser(@RequestBody AppUser user) throws ErrorMessageException {
        AppUser oldUser = _userRepository.findByUsername(user.getUsername());
        if (oldUser == null) {
            throw new ErrorMessageException(String.format("User with username: %s does not exist!", user.getUsername()),
                    HttpStatus.FORBIDDEN);
        }
        for (Role role : user.getRoles()) {
            Optional<Role> oldRole = _roleRepository.findByIdAndName(role.getId(), role.getName());
            if (!oldRole.isPresent()) {
                throw new ErrorMessageException(String.format("Could not update role %s with id %d for user!", role.getName(), role.getId()),
                        HttpStatus.FORBIDDEN);
            }
        }
        try {
            user.setPassword(_bCryptPasswordEncoder.encode(user.getPassword()));
            _userRepository.save(user);
        } catch (EmptyResultDataAccessException e) {
            throw new ErrorMessageException(String.format("Could not update user %s!", user.getUsername()),
                    HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }


    private void insideService(Service service) {
        String tmp = "http://localhost:" + service.getProvider().getPort() + "/" + service.getProvider().getUrl().split("\\.")[0] + service.getUrl();
        for (Endpoint endpoint : service.getEndpoints()) {
            endpoint.setService(service);
            for (EndpointMetaScheme endpointMetaScheme : endpoint.getMetaSchemes()) {
                endpointMetaScheme.setEndpoint(endpoint);
                for (EndpointMetaSchemeAttribute endpointMetaSchemeAttribute : endpointMetaScheme.getMetaSchemeAttributes()) {
                    endpointMetaSchemeAttribute.setEndpointMetaScheme(endpointMetaScheme);
                }
            }
            for (SecurityPolicy policy : endpoint.getPolicies()) {
                policy.setEndpoint(endpoint);
                Optional<Role> role = _roleRepository.findById(policy.getRole().getId());
                Optional<PolicyType> type = _policyTypeRepository.findById(policy.getType().getId());
                if (!role.isPresent() || !type.isPresent()) {
                    //greska
                    logger.error("Role doesn't exist...");
                } else {
                    policy.setRole(role.get());
                    policy.setType(type.get());
                }
                SecurityPolicyDTO securityPolicyDTO = new SecurityPolicyDTO(tmp + endpoint.getUrl(),
                        policy.getFilters(),
                        policy.getRole().getId().toString(),
                        policy.getType().getId().toString());
                securityPolicyDTOList.add(securityPolicyDTO);
            }
        }
    }
}
