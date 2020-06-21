package com.se.source.security.filters;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.source.auth.domain.AppUser;
import com.auth0.jwt.JWT;
import com.se.source.auth.repositories.IUserRepository;
import com.se.source.auth.utils.AuthConstants;
import com.se.source.broker.implementation.model.Broker;
import com.se.source.security.response.AuthenticationResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager _authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        _authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            return _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwtToken = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(AuthConstants.SECRET));
        response.addHeader("Access-Control-Expose-Headers", AuthConstants.HEADER_STRING);
        response.addHeader(AuthConstants.HEADER_STRING, AuthConstants.TOKEN_PREFIX + jwtToken);
        AuthenticationResponseObject responseObject = new AuthenticationResponseObject();
        responseObject.setUsername(((User) authResult.getPrincipal()).getUsername());
        responseObject.setAuthenticated(true);
        responseObject.setGrantedAuthorities(
                authResult.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
        String responseObjectJson = new ObjectMapper().writeValueAsString(responseObject);
        response.getWriter().write(responseObjectJson);
        response.flushBuffer();
    }
}
