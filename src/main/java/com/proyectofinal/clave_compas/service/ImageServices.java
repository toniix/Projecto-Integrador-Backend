package com.proyectofinal.clave_compas.service;


import com.proyectofinal.clave_compas.bd.clavecompas.entities.ImageEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ImageServices {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    
    private final ImageRepository imageRepository;

    // FIXME: En Lugar de este contructor usamos RequiredArgsConstructor
    /*public ImageServices(@Autowired ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }*/

    public void saveImage(MultipartFile file, ProductEntity product) {
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
            createImagen("/uploads/" + fileName, product);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    private void createImagen(String ruta, ProductEntity product) {
        ImageEntity image = new ImageEntity();
        image.setImageUrl(ruta);
        if (product.getImages() == null) {
            product.setImages(new ArrayList<>());
        }
        product.getImages().add(image);
        image.setProduct(product);
        imageRepository.save(image);
    }
}
