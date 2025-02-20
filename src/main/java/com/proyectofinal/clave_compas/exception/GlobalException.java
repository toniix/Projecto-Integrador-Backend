package com.proyectofinal.clave_compas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BodyException> tratamientoRNFE(ResourceNotFoundException rnfe){
        BodyException bodyException = BodyException.builder()
                                        .statusCode(HttpStatus.NOT_FOUND.value())
                                        .message(rnfe.getMessage())
                                        .build();
        return new ResponseEntity<>(bodyException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BodyException> tratamientoRNFE(BadRequestException bre){
        BodyException bodyException = BodyException.builder()
                                    .statusCode(HttpStatus.NOT_FOUND.value())
                                    .message(bre.getMessage())
                                    .build();
        return new ResponseEntity<>(bodyException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyOnRepositoryException.class)
    public ResponseEntity<BodyException> tratamientoRNFE(ProductAlreadyOnRepositoryException paore){
        BodyException bodyException = BodyException.builder()
                .statusCode(HttpStatus.CONFLICT.value()).message(paore.getMessage()).build();
        return new ResponseEntity<>(bodyException,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BodyException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        BodyException bodyException = BodyException.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Parametros no validos").NotValidParams(errors).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyException);
    }

    @ExceptionHandler(NotValidCategory.class)
    public ResponseEntity<BodyException> tratamientoRNFE(NotValidCategory bre){
        BodyException bodyException = BodyException.builder().statusCode(HttpStatus.CONFLICT.value())
                .message(bre.getMessage()).build();
        return new ResponseEntity<>(bodyException,HttpStatus.CONFLICT);
    }
}
