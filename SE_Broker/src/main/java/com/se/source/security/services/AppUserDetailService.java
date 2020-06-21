package com.se.source.security.services;

import com.se.source.auth.domain.AppUser;
import com.se.source.auth.domain.Role;
import com.se.source.auth.repositories.IRoleRepository;
import com.se.source.auth.repositories.IUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserDetailService implements UserDetailsService {

    private IUserRepository _userRepository;
    private IRoleRepository _roleRepository;

    public AppUserDetailService(IUserRepository userRepository, IRoleRepository roleRepository) {
        _userRepository = userRepository;
        _roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser appUser = _userRepository.findByUsername(s);
        if (appUser == null) throw new UsernameNotFoundException(s);
        return new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(AppUser appUser) {
        Collection<Role> roles = _roleRepository.findAllByAppUsers(appUser);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }
}
