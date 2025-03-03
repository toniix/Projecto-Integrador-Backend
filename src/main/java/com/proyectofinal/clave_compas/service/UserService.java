package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.RolEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.UserRolEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.UserRepository;
import com.proyectofinal.clave_compas.controller.responses.LoginResponse;
import com.proyectofinal.clave_compas.exception.ResourceNotFoundException;
import com.proyectofinal.clave_compas.exception.UserAlreadyOnRepositoryException;
import com.proyectofinal.clave_compas.mappers.UserMapper;
import com.proyectofinal.clave_compas.security.jwt.JwtService;
import com.proyectofinal.clave_compas.security.userdetail.UserDetailIsImpl;
import com.proyectofinal.clave_compas.service.dto.LoginDTO;
import com.proyectofinal.clave_compas.service.dto.UserDTO;
import com.proyectofinal.clave_compas.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRolService userRolService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class})
    public UserDTO saveUser(UserDTO userDTO) throws UserAlreadyOnRepositoryException {
        Optional.ofNullable(userDTO.email())
                .flatMap(userRepository::findByEmail)
                .ifPresent(user->{
                    throw new UserAlreadyOnRepositoryException(String.format("El email %s ya existe",userDTO.email()));
                });

        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.password()));
        userEntity = userRepository.save(userEntity);
        RolEntity rolEntity = rolService.findByRolName(Constants.DEFAULT_ROL);
        UserRolEntity userRolEntity = UserRolEntity.builder()
                .user(userEntity)
                .enable(true)
                .role(rolEntity)
                .build();
        userRolService.save(userRolEntity);
        return UserMapper.INSTANCE.toDTO(userEntity);
    }
    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class})
    public LoginResponse loginUser(LoginDTO loginDTO) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        UserEntity userEntity = userRepository.findByEmail(loginDTO.email()).orElseThrow();
        Set<String> roles = userRepository.findEnabledRolesByUserId(userEntity.getId());
        userEntity.setRoles(roles);
        UserDetailIsImpl user= new UserDetailIsImpl(userEntity);
        String token=jwtService.getToken(user);
        return new LoginResponse(UserMapper.INSTANCE.toDTO(userEntity), token);
    }

    public Page<UserDTO> getPaginateUsers(int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(pageable);
        return UserMapper.INSTANCE.toDTOs(users);
    }

    public UserDTO getById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("El usurio con ID " + id + " no existe."));
        return UserMapper.INSTANCE.toDTO(userEntity);
    }
}
