package com.yassine.ecommerceapi.service;

import com.yassine.ecommerceapi.Dto.RegisterRequest;
import com.yassine.ecommerceapi.Dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO register(RegisterRequest request);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void deleteUser(Long id);
}

