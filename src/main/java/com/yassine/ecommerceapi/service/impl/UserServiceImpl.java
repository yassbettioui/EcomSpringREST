package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.RegisterRequest;
import com.yassine.ecommerceapi.Dto.UserDTO;
import com.yassine.ecommerceapi.Entity.User;
import com.yassine.ecommerceapi.mapper.UserMapper;
import com.yassine.ecommerceapi.repository.UserRepository;
import com.yassine.ecommerceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO register(RegisterRequest request) {
        // Mapper RegisterRequest → User
        User user = userMapper.registerRequestToUser(request);

        // Sauvegarder dans la base de données
        user = userRepository.save(user);

        // Retourner UserDTO
        return userMapper.userToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
