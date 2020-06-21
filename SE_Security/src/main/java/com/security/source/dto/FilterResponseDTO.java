package com.security.source.dto;

import lombok.Data;

@Data
public class FilterResponseDTO {

    public String runningServiceURL;
    public String response;//podaci koje bi trebalo da se filtriraju
    public String userRole;

    public FilterResponseDTO(String runningServiceURL, String response, String userRole) {
        this.runningServiceURL = runningServiceURL;
        this.response = response;
        this.userRole = userRole;
    }
}