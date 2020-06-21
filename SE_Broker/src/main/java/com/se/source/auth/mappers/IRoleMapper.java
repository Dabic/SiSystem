package com.se.source.auth.mappers;

import com.se.source.auth.domain.Role;
import com.se.source.auth.dtos.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    IRoleMapper instance = Mappers.getMapper(IRoleMapper.class);

    RoleDTO roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDTO appUserDTO);
}
