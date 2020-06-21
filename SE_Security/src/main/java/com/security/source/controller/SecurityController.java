package com.security.source.controller;

import com.security.source.dto.FilterResponseDTO;
import com.security.source.dto.SecurityPolicyDTO;
import com.security.source.repository.MySQLRepository;
import com.security.source.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(Constants.API_URL)
public class SecurityController {

    public MySQLRepository _mySQLRepository;
    private Logger logger = LoggerFactory.getLogger(SecurityController.class);

    public SecurityController(MySQLRepository mySQLRepository) {
        _mySQLRepository = mySQLRepository;
    }

    @RequestMapping("/save/")
    public ResponseEntity<String> save(@RequestBody List<SecurityPolicyDTO> securityPolicies) {

        for (SecurityPolicyDTO dto : securityPolicies) {
            logger.error("ovo je u sfc " + dto);
        }
        boolean success = _mySQLRepository.save(securityPolicies);
        if (!success) {
            return new ResponseEntity<>("not possible", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("saved", HttpStatus.OK);
    }

    @RequestMapping("/filter/")
    public ResponseEntity<String> filter(@RequestBody FilterResponseDTO filterResponseDTO) {

        logger.error("ovo je u sfc " + filterResponseDTO);
        String filteredResponse = _mySQLRepository.filter(filterResponseDTO);
        logger.error("filtrirani: " + filteredResponse);
        if (filteredResponse.equals(""))// ili ako je NONE ili neka od drugih gresaka, mogli bismo slati razlicite httpstatuse
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(filteredResponse, HttpStatus.OK);
    }
}
