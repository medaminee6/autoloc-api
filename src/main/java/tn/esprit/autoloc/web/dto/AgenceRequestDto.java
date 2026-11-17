package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgenceRequestDto(
        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
        String nom,

        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 100, message = "La ville ne doit pas depasser 100 caracteres")
        String ville,

        @NotBlank(message = "L'adresse est obligatoire")
        @Size(max = 255, message = "L'adresse ne doit pas depasser 255 caracteres")
        String adresse,

        @NotBlank(message = "Le telephone est obligatoire")
        @Pattern(regexp = "\\d{8}", message = "Le telephone doit contenir exactement 8 chiffres")
        String telephone
) {
}
