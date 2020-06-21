package com.se.source.auth.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AppUserDTO {
    String username;
    String email;
    List<RoleDTO> roles;
}
