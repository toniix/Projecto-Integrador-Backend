package com.pi.proyectoClaveCompas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    @Autowired
    private RestTemplate restTemplate;

    // Reemplaza YOUR_ACCESS_KEY con tu API key de Unsplash
    private final String ACCESS_KEY = "ZhRMbM45I0OPHvCkoPC8qzqHWHFCbug8F-FHIdoqZdY";

    // 1. Endpoint para obtener 10 productos aleatorios
    @GetMapping("/random-list")
    public ResponseEntity<?> getRandomProductos() {
        // Usamos la búsqueda de Unsplash con per_page=10
        String url = "https://api.unsplash.com/search/photos?query=instrumentos+musicales&per_page=10&client_id=" + ACCESS_KEY;
        String response = restTemplate.getForObject(url, String.class);
        // Opcional: aquí se podría procesar la respuesta para desordenarla o mapearla a un DTO
        return ResponseEntity.ok(response);
    }

    // 2. Endpoint para obtener el detalle de un producto por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable String id) {
        // Llama a Unsplash para obtener los detalles de la imagen
        String url = "https://api.unsplash.com/photos/" + id + "?client_id=" + ACCESS_KEY;
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }

    // 3. Endpoint para obtener imágenes adicionales (galería) para el producto
    @GetMapping("/{id}/galeria")
    public ResponseEntity<?> getProductoGaleria(@PathVariable String id) {
        // Para simplificar, se realiza otra búsqueda de Unsplash (podrías filtrar para omitir la imagen principal)
        String url = "https://api.unsplash.com/search/photos?query=instrumentos+musicales&per_page=5&client_id=" + ACCESS_KEY;
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}