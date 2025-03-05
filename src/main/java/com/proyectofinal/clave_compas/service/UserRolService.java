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
    private final UserService userService;
    private final RolService rolService;

    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, SQLException.class})
    public UserRolEntity save(UserRolEntity userRolEntity) {
        return userRolRepository.save(userRolEntity);
    }
    
    @Transactional(transactionManager = "txManagerClavecompas", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,SQLException.class})
    public boolean asignRoles(UserRolRequest userRolRequest) {
        UserEntity user = userService.findById(userRolRequest.getIdUser());
        Set<UserRolEntity> userRolEntities = userRolRepository.findByUserId(user.getId());
        Set<Long> rolIds = userRolRequest.getIdsRol();
        Set<UserRolEntity>  userRolsToAdd = new HashSet<>();
        if(!rolIds.isEmpty()) {
            if(userRolEntities.isEmpty() ) {

                for(Long rolId : rolIds){
                    RolEntity rolEntity = RolEntity.builder().id(rolId).build();
                    UserRolEntity userRolEntity = UserRolEntity.builder()
                            .role(rolEntity)
                            .user(user)
                            .enable(true)
                            .build();
                    userRolsToAdd.add(userRolEntity);
                }
                userRolRepository.saveAll(userRolsToAdd);

            }else {
                Map<Long, UserRolEntity> actualAsigners = userRolEntities.stream()
                        .collect(Collectors.toMap(ure -> ure.getRole().getId(), ure -> ure));

                for(Long rolId : rolIds){
                    if(actualAsigners.containsKey(rolId)){
                        if(!actualAsigners.get(rolId).getEnable()){
                            actualAsigners.get(rolId).setEnable(true);
                            userRolsToAdd.add(actualAsigners.get(rolId));
                        }
                        actualAsigners.remove(rolId);
                    }else {
                        RolEntity rolEntity = RolEntity.builder().id(rolId).build();
                        UserRolEntity userRolEntity = UserRolEntity.builder()
                                .role(rolEntity)
                                .user(user)
                                .enable(true)
                                .build();
                        userRolsToAdd.add(userRolEntity);
                    }
                }
                if (!actualAsigners.isEmpty()) {
                    actualAsigners.forEach((rolId,rue)->{
                        rue.setEnable(false);
                        userRolsToAdd.add(rue);
                    });
                }
                userRolRepository.saveAll(userRolsToAdd);
            }
        }else {
            if (!userRolEntities.isEmpty()) {
                userRolEntities.forEach(ure->ure.setEnable(false));
                userRolRepository.saveAll(userRolEntities);
            }
        }

        return true;
    }





}
