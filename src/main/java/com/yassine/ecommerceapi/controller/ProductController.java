package com.yassine.ecommerceapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yassine.ecommerceapi.Dto.ProductDTO;
import com.yassine.ecommerceapi.Dto.ProductRequest;
import com.yassine.ecommerceapi.Entity.Category;
import com.yassine.ecommerceapi.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")

public class ProductController {
@Autowired
    private final ProductService productService;
    private final Path imageStorageLocation = Path.of("uploads/products").toAbsolutePath().normalize();
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> create(@Valid @RequestPart("product") String productJson,
                                             @RequestPart("image") MultipartFile imageFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ProductRequest request = mapper.readValue(productJson, ProductRequest.class);
        return ResponseEntity.ok(productService.createProduct(request,imageFile));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        // Log pour débogage
        System.out.println("Received update for product ID: " + id);
        System.out.println("Product data: " + productRequest);
        System.out.println("Image present: " + (imageFile != null));

        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productRequest, imageFile);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(id));
    }
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(productService.getProductsByCategoryName(categoryName));
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveProductImage(@PathVariable String filename) {
        try {
            // 1. Validation et sécurisation du nom de fichier
            String sanitizedFilename = Objects.requireNonNull(filename)
                    .replace("..", "")
                    .replace("/", "");

            // 2. Construction du chemin sécurisé
            Path filePath = imageStorageLocation.resolve(sanitizedFilename).normalize();

            if (!filePath.startsWith(imageStorageLocation)) {
                return ResponseEntity.badRequest().build();
            }

            // 3. Chargement avec le nouveau système de ressources
            Resource resource = new UrlResource(filePath.toUri());

            // 4. Retour avec cache et headers optimisés
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(Duration.ofDays(365)).cachePublic())
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
