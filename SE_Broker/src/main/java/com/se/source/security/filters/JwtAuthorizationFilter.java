package com.se.source.security.filters;

import com.auth0.jwt.algorithms.Algorithm;
import com.se.source.auth.utils.AuthConstants;
import com.auth0.jwt.JWT;
import com.se.source.broker.implementation.model.Broker;
import com.se.source.security.services.AppUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private AppUserDetailService _appUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AppUserDetailService appUserDetailsService) {
        super(authenticationManager);
        _appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AuthConstants.HEADER_STRING);
        if (header == null || !header.startsWith(AuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = getAuthentication(request);
        Logger logger;
        logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AuthConstants.HEADER_STRING);
        if (token == null)
            return null;

        String username = JWT.require(Algorithm.HMAC512(AuthConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(AuthConstants.TOKEN_PREFIX, ""))
                .getSubject();

        if (username == null)
            return null;

        UserDetails userDetails = _appUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
