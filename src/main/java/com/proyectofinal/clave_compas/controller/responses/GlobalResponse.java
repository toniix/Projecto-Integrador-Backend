package com.proyectofinal.clave_compas.controller.responses;

import java.io.Serializable;

public class GlobalResponse implements Serializable {
    private int statusCode;
    private String message;
    private Object data;
    public GlobalResponse(int statusCode, String message, Object data) {}
}
