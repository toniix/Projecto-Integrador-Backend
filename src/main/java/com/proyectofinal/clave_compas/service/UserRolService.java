package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.RolEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserRolEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.UserRolRepository;
import com.proyectofinal.clave_compas.controller.requests.UserRolRequest;
import com.proyectofinal.clave_compas.controller.responses.UserRolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRolService {

    private final UserRolRepository userRolRepository;

    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class})
    public UserRolEntity save(UserRolEntity userRolEntity) {
        return userRolRepository.save(userRolEntity);
    }


}
