package com.proyectofinal.clave_compas.controller;


import com.proyectofinal.clave_compas.controller.responses.ApiResponse;
import com.proyectofinal.clave_compas.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @Autowired
    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImage(
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse<>(false, "Please upload a file", null));
            }

            // Check if the file is an image
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse<>(false, "File must be an image", null));
            }

            Map<String, String> uploadResult = cloudinaryService.uploadImage(file);

            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Image uploaded successfully", uploadResult));

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to upload image: " + e.getMessage(), null));
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> uploadMultipleImages(
            @RequestParam("files") List<MultipartFile> files) {

        try {
            // Validate request
            if (files == null || files.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse<>(false, "Please upload at least one file", null));
            }

            // Upload all images and collect results
            List<Map<String, String>> results = files.stream()
                    .filter(file -> !file.isEmpty() &&
                            file.getContentType() != null &&
                            file.getContentType().startsWith("image/"))
                    .map(file -> {
                        try {
                            return cloudinaryService.uploadImage(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Images uploaded successfully", results));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to upload images: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable String publicId) {
        boolean deleted = cloudinaryService.deleteImage(publicId);

        if (deleted) {
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse<>(true, "Image deleted successfully", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to delete image", null));
        }
    }
}

