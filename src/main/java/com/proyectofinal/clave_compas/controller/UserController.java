package com.proyectofinal.clave_compas.controller;

import com.proyectofinal.clave_compas.controller.responses.GlobalResponse;
import com.proyectofinal.clave_compas.controller.responses.UserRolResponse;
import com.proyectofinal.clave_compas.service.RolService;
import com.proyectofinal.clave_compas.service.UserService;
import com.proyectofinal.clave_compas.service.dto.LoginDTO;
import com.proyectofinal.clave_compas.service.dto.RolDTO;
import com.proyectofinal.clave_compas.service.dto.UserDTO;
import com.proyectofinal.clave_compas.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
//@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class UserController {

    private final UserService userService;
    private final RolService rolService;

    public UserController(@Lazy UserService userService, RolService rolService) {
        this.userService = userService;
        this.rolService = rolService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<GlobalResponse> saveUser(@Validated @RequestBody UserDTO userDTO) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(userService.saveUser(userDTO))
                .build();
        return ResponseEntity.ok(gres);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<GlobalResponse> loginUser(@Validated @RequestBody LoginDTO loginDTO) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(userService.loginUser(loginDTO))
                .build();
        return ResponseEntity.ok(gres);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> findAll(@RequestParam int page, @RequestParam int pageSize) {
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(userService.getPaginateUsers(page, pageSize))
                .build();
        return ResponseEntity.ok(gres);
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<GlobalResponse> findUserById(@PathVariable Long idUser) {
        UserDTO userDTO = userService.getById(idUser);
        List<RolDTO> roles = rolService.findByUserId(idUser);
        GlobalResponse gres = GlobalResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.MENSAJE_EXITO)
                .response(new UserRolResponse(userDTO,roles))
                .build();
        return ResponseEntity.ok(gres);
    }
}
