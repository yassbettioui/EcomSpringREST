package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.RegisterRequest;
import com.yassine.ecommerceapi.Dto.UserDTO;
import com.yassine.ecommerceapi.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Pour créer un User depuis un RegisterRequest
    User registerRequestToUser(RegisterRequest registerRequest);

    // Si tu veux exposer un UserDTO aussi
    UserDTO userToUserDTO(User user);
}

