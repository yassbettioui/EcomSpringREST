package com.yassine.ecommerceapi.repository;

import com.yassine.ecommerceapi.Entity.Category;
import com.yassine.ecommerceapi.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategory(Category category);
}
