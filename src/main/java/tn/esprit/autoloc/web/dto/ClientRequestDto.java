package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequestDto(
        @NotBlank(message = "Le nom du client est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
        String nom,

        @NotBlank(message = "Le prenom du client est obligatoire")
        @Size(max = 100, message = "Le prenom ne doit pas depasser 100 caracteres")
        String prenom,

        @NotBlank(message = "L'e-mail du client est obligatoire")
        @Email(message = "Le format de l'e-mail est invalide")
        String email,

        @NotBlank(message = "Le telephone est obligatoire")
        @Pattern(regexp = "\\d{8}", message = "Le telephone doit contenir exactement 8 chiffres")
        String telephone,

        @NotBlank(message = "Le numero de permis est obligatoire")
        @Size(max = 50, message = "Le numero de permis ne doit pas depasser 50 caracteres")
        String numPermis,

        @NotNull(message = "La date d'inscription est obligatoire")
        @PastOrPresent(message = "La date d'inscription ne peut pas etre dans le futur")
        LocalDate dateInscription
) {
}
