package com.se.source.security.config;

import com.se.source.auth.repositories.IUserRepository;
import com.se.source.auth.utils.AuthConstants;
import com.se.source.broker.utils.BrokerConstants;
import com.se.source.security.filters.JwtAuthenticationFilter;
import com.se.source.security.filters.JwtAuthorizationFilter;
import com.se.source.security.services.AppUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder _bCryptPasswordEncoder;
    private AppUserDetailService _appUserDetailService;
    private IUserRepository _userRepository;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, AppUserDetailService appUserDetailService, IUserRepository userRepository) {
        _bCryptPasswordEncoder = bCryptPasswordEncoder;
        _appUserDetailService = appUserDetailService;
        _userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(AuthConstants.API_URL).permitAll()
//                .antMatchers(BrokerConstants.API_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), _appUserDetailService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(_appUserDetailService).passwordEncoder(_bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
