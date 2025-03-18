package com.proyectofinal.clave_compas.service;

import com.proyectofinal.clave_compas.bd.clavecompas.entities.ProductEntity;
import com.proyectofinal.clave_compas.bd.clavecompas.repositories.ProductRepository;
import com.proyectofinal.clave_compas.dto.ProductSearchResultDTO;
import com.proyectofinal.clave_compas.dto.SearchRequestDTO;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;
    private final ReservationService reservationService;

    public Page<ProductSearchResultDTO> searchProducts(SearchRequestDTO request) {
        // Crear la ordenación
        Sort sort = Sort.by(
                request.getSortDirection().equalsIgnoreCase("asc") ?
                        Sort.Direction.ASC : Sort.Direction.DESC,
                request.getSortBy());

        // Crear la paginación
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort);

        // Realizar la búsqueda inicial
        Page<ProductEntity> productsPage = productRepository.advancedSearch(
                request.getKeyword(),
                request.getCategoryId(),
                request.getMinPrice(),
                request.getMaxPrice(),
                pageable);

        // Si no hay fechas, simplemente mapear los resultados
        if (request.getStartDate() == null || request.getEndDate() == null || request.getQuantity() == null) {
            return productsPage.map(this::mapToDTO);
        }

        // Filtrar por disponibilidad de fechas
        List<ProductSearchResultDTO> filteredList = productsPage.getContent().stream()
                .map(product -> {
                    ProductSearchResultDTO dto = mapToDTO(product);
                    // Verificar disponibilidad
                    boolean isAvailable = reservationService.isProductAvailable(
                            product.getIdProduct(),
                            request.getStartDate(),
                            request.getEndDate(),
                            request.getQuantity());

                    dto.setIsAvailable(isAvailable);
                    return dto;
                })
                .filter(ProductSearchResultDTO::getIsAvailable)
                .collect(Collectors.toList());

        // Crear un nuevo Page con los resultados filtrados
        return new PageImpl<>(
                filteredList,
                pageable,
                filteredList.size() // Nota: esto no es preciso para el total count, pero es lo mejor que podemos hacer
        );
    }

    private ProductSearchResultDTO mapToDTO(ProductEntity product) {
        return ProductSearchResultDTO.builder()
                .idProduct(product.getIdProduct())
                .name(product.getName())
                .brand(product.getBrand())
                .model(product.getModel())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .mainImageUrl(product.getImages() != null && !product.getImages().isEmpty() ?
                        product.getImages().get(0).getImageUrl() : null)
                .isAvailable(product.getAvailable())
                .availableStock(product.getStock())
                .build();
    }

    public List<String> getAutocompleteSuggestions(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String prefix = query.trim();

        // Combinar diferentes tipos de sugerencias
        List<String> nameSuggestions = productRepository.findSuggestionsByPrefix(prefix);
        List<String> brandSuggestions = productRepository.findBrandSuggestionsByPrefix(prefix);
        List<String> modelSuggestions = productRepository.findModelSuggestionsByPrefix(prefix);

        // Combinar las sugerencias y limitar el total
        return Stream.of(nameSuggestions, brandSuggestions, modelSuggestions)
                .flatMap(List::stream)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }
}