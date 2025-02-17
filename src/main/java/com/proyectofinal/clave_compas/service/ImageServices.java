package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.ImageEntity;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.InstrumentEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ImageServices {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    private HashMap<Integer, ImageEntity> imagenes ; // CAMBIAR A REPOSITORIO

    public ImageServices() {
        this.imagenes = new HashMap<>();
    }

    public void saveImage(MultipartFile file, InstrumentEntity instrumento) {
        if (file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());
            createImagen("/uploads/" + fileName, instrumento);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    private void createImagen(String ruta, InstrumentEntity instrumento) {
        ImageEntity imagen = new ImageEntity();
        imagen.setImageUrl(ruta);
        if (instrumento.getImages() == null) {
            instrumento.setImages(new ArrayList<>());
        }
        instrumento.getImages().add(imagen);
        imagenes.put(imagen.getIdImage(),imagen); // GUARDO POR AHORA --> CAMBIAR A SAVE EN BD
    }
}
