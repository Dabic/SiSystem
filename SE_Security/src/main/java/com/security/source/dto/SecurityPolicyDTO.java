package com.security.source.dto;

import lombok.Data;

@Data
public class SecurityPolicyDTO {

    public String url;
    public String filters;
    public String role;
    public String type;

    public SecurityPolicyDTO(String url, String filters, String role, String type) {
        this.url = url;
        this.filters = filters;
        this.role = role;
        this.type = type;
    }
}
