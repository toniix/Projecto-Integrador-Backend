package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.User;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUserCode(String userCode) {
        return userRepository.findByUserCode(userCode);
    }
}
