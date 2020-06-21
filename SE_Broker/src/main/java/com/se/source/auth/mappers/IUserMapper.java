package com.se.source.auth.mappers;

import com.se.source.auth.domain.AppUser;
import com.se.source.auth.dtos.AppUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper instance = Mappers.getMapper(IUserMapper.class);

    AppUserDTO appUserToAppUserDto(AppUser appUser);

    AppUser appUserDtoToAppUser(AppUserDTO appUserDTO);

}
