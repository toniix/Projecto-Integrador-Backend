package com.proyectofinal.clave_compas.controller;


import com.proyectofinal.clave_compas.service.ProductServices;
import com.proyectofinal.clave_compas.service.dto.InstrumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "instruments")
@RequiredArgsConstructor
public class ProductController {

    /*private final ProductServices productServices;

    private final ImageServices imageServices;

    public ProductController(@Autowired ProductServices productServices, ImageServices imageServices) {
        this.productServices = productServices;
        this.imageServices = imageServices;
    }

    @PostMapping
    private ResponseEntity<Instrumento> registrarProducto(@RequestParam String instrumentoDtoJson,
                                                          @RequestParam("file") MultipartFile file) throws NotValidInstrumentData, FileNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InstrumentoDto instrumentoDto = objectMapper.readValue(instrumentoDtoJson, InstrumentoDto.class);
            if (!instrumentoDto.isValid()) throw new NotValidInstrumentData("Argumentos no validos");
            if (file.isEmpty()) throw new FileNotFoundException();

            Instrumento instrumento = instrumentoDto.toEntity();
            imageServices.saveImage(file, instrumento);
            productServices.saveProduct(instrumento, instrumentoDto);
            return ResponseEntity.ok(instrumento);
        } catch (ProductAlreadyOnRepositoryException e) {
            return ResponseEntity.badRequest().build();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }*/

    private final ProductServices productServices;

    @GetMapping
    public ResponseEntity<Page<InstrumentDTO>> findAll(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(productServices.getAllInstruments(page, pageSize));
    }
}
