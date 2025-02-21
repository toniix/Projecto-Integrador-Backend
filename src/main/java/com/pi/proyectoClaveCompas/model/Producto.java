package com.pi.proyectoClaveCompas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Producto {

    private String id;

    @JsonProperty("alt_description")
    private String altDescription;

    private String description;

    // Usamos un Map para representar el objeto 'urls' que Unsplash retorna,
    // el cual contiene distintas versiones (raw, full, regular, small, thumb)
    private Map<String, String> urls;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }
}