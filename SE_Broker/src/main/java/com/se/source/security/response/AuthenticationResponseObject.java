package com.se.source.security.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationResponseObject {
    private String username;
    private List<String> grantedAuthorities;
    private boolean authenticated;
}
