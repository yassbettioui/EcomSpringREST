package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.CategoryDTO;
import com.yassine.ecommerceapi.Dto.CategoryDTO;
import com.yassine.ecommerceapi.Entity.Category;

public class CategoryMapper {

    // Mapping d'une entité vers un DTO
    public static CategoryDTO toDto(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    // Mapping d'un DTO vers une entité
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        return Category.builder()
                .id(dto.getId()) // ou null si on veut ignorer l'id
                .name(dto.getName())
                .build();
    }
}

