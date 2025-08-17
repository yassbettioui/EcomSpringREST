package com.yassine.ecommerceapi.controller;


import com.yassine.ecommerceapi.Dto.RegisterRequest;
import com.yassine.ecommerceapi.Dto.UserDTO;
import com.yassine.ecommerceapi.mapper.UserMapper;
import com.yassine.ecommerceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        UserDTO userDTO = userService.register(request);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();
       return ResponseEntity.ok(allUsers);
    }
}
