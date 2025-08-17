package com.yassine.ecommerceapi.service;

import com.yassine.ecommerceapi.Dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO create(CategoryDTO dto);
    List<CategoryDTO> getAll();
    CategoryDTO getById(Long id);
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
}
