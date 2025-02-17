package com.proyectofinal.clave_compas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BodyException> tratamientoRNFE(ResourceNotFoundException rnfe){
        BodyException bodyException = new BodyException(HttpStatus.NOT_FOUND.value(),rnfe.getMessage());
        return new ResponseEntity<>(bodyException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BodyException> tratamientoRNFE(BadRequestException bre){
        BodyException bodyException = new BodyException(HttpStatus.BAD_REQUEST.value(),bre.getMessage());
        return new ResponseEntity<>(bodyException,HttpStatus.BAD_REQUEST);
    }
}
