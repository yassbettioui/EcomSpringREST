package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.ProductDTO;
import com.yassine.ecommerceapi.Dto.ProductRequest;
import com.yassine.ecommerceapi.Entity.Category;
import com.yassine.ecommerceapi.Entity.Product;
import com.yassine.ecommerceapi.exception.ResourceNotFoundException;
import com.yassine.ecommerceapi.mapper.ProductMapper;
import com.yassine.ecommerceapi.repository.CategoryRepository;
import com.yassine.ecommerceapi.repository.ProductRepository;
import com.yassine.ecommerceapi.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ProductDTO createProduct(ProductRequest request, MultipartFile imageFile) throws IOException, IOException {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // Save image to disk (par exemple dans /uploads/products/)
        String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String uploadDir = "uploads/products/";
        Path imagePath = Paths.get(uploadDir + imageName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());
product.setQuantity(request.getQuantity());
        product.setImageUrl("/images/" + imageName); // stocker l’URL relative
        product.setCategory(categoryRepository.findById(request.getCategoryId()).get());

        productRepository.save(product);
        return ProductMapper.toDto(product); // si tu utilises MapStruct ou manuel
    }


    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id " + id));
        return ProductMapper.toDto(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductRequest request, MultipartFile imageFile) throws IOException {
        // 1. Récupérer le produit existant
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));

        // 2. Mettre à jour les champs de base
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        // 3. Gestion de la catégorie
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        // 4. Gestion de l'image
        if (imageFile != null && !imageFile.isEmpty()) {
            // Supprimer l'ancienne image si elle existe
            if (product.getImageUrl() != null) {
                Path oldImagePath = Paths.get("uploads/products/" + product.getImageUrl().replace("/images/", ""));
                Files.deleteIfExists(oldImagePath);
            }

            // Enregistrer la nouvelle image
            String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "uploads/products/";
            Path imagePath = Paths.get(uploadDir + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageFile.getBytes());
            product.setImageUrl("/images/" + imageName);
        }

        // 5. Sauvegarder et retourner le DTO
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id " + id));
        productRepository.delete(product);
    }
    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> byCategoryId = productRepository.findByCategoryId(categoryId);
        return byCategoryId.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
@Override
    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée : " + categoryName));
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }


}
