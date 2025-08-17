package com.yassine.ecommerceapi.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    private double price;

    private String imageUrl;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private int quantity;

    private Long categoryId; // 🔥 Nouveau champ pour lier à une catégorie existante

}
