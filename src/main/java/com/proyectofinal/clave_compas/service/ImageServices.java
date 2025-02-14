package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.model.Imagen;
import com.proyectofinal.clave_compas.model.Instrumento;
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


    private HashMap<Long, Imagen> imagenes ; // CAMBIAR A REPOSITORIO

    public ImageServices() {
        this.imagenes = new HashMap<>();
    }

    public void saveImage(MultipartFile file, Instrumento instrumento) {
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

    private void createImagen(String ruta, Instrumento instrumento) {
        Imagen imagen = new Imagen();
        imagen.setUrl(ruta);
        if (instrumento.getImagenes() == null) {
            instrumento.setImagenes(new ArrayList<>());
        }
        instrumento.getImagenes().add(imagen);
        imagenes.put(imagen.getId_imagen(),imagen); // GUARDO POR AHORA --> CAMBIAR A SAVE EN BD
    }
}
