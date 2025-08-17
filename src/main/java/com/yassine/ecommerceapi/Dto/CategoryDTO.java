package com.yassine.ecommerceapi.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;
}
