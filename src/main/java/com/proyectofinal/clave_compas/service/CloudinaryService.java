package com.proyectofinal.clave_compas.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Uploads an image to Cloudinary and returns the URL
     *
     * @param file the image file to upload
     * @return Map containing upload results including the secure URL
     * @throws IOException if upload fails
     */
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "auto");
            options.put("folder", "instruments"); // Organize uploads in folders

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

            Map<String, String> result = new HashMap<>();
            result.put("publicId", (String) uploadResult.get("public_id"));
            result.put("url", (String) uploadResult.get("url"));
            result.put("secureUrl", (String) uploadResult.get("secure_url"));

            return result;
        } catch (IOException e) {
            throw new IOException("Error uploading image to Cloudinary", e);
        }
    }

    /**
     * Deletes an image from Cloudinary by its public ID
     *
     * @param publicId the public ID of the image to delete
     * @return true if deletion was successful
     */
    public boolean deleteImage(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            return false;
        }
    }
}
