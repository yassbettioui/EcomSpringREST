package com.yassine.ecommerceapi.Dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // PAS de mot de passe ici ! 🔥 (jamais envoyer le password dans un DTO exposé)
}

