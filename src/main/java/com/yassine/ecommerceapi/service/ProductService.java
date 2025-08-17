package com.yassine.ecommerceapi.service;


import com.yassine.ecommerceapi.Dto.ProductDTO;
import com.yassine.ecommerceapi.Dto.ProductRequest;
import com.yassine.ecommerceapi.Entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

  //  ProductDTO createProduct(ProductRequest request);

    ProductDTO createProduct(ProductRequest request, MultipartFile imageFile) throws IOException;

    ProductDTO getProductById(Long id);

    List<ProductDTO> getAllProducts();

    ProductDTO updateProduct(Long id, ProductRequest request, MultipartFile imageFile) throws IOException;

    void deleteProduct(Long id);

    List<ProductDTO> getProductsByCategoryId(Long id);
    List<ProductDTO> getProductsByCategoryName(String categoryName);
}

