# DTO, validation et gestion des erreurs

## Architecture retenue

Les endpoints exposes pour `Agence`, `Client` et `Vehicule` n'echangent plus
directement les entites JPA.

Le parcours d'une requete est maintenant :

1. le controleur recoit un `...RequestDto` et declenche la validation avec
   `@Valid` ;
2. le service applique le cas d'utilisation ;
3. le mapper transforme le DTO en entite et l'entite en `...ResponseDto` ;
4. le repository persiste uniquement les entites JPA ;
5. le controleur retourne un DTO sans association JPA ni champ technique.

## DTO disponibles

| Ressource | Entree | Sortie |
|---|---|---|
| Agence | `AgenceRequestDto` | `AgenceResponseDto` |
| Client | `ClientRequestDto` | `ClientResponseDto` |
| Vehicule | `VehiculeRequestDto` | `VehiculeResponseDto` |

Pour un vehicule, l'agence est envoyee avec `agenceId`. La reponse fournit
`agenceId` et `agenceNom`, sans exposer l'entite `Agence` complete.

## Regles de validation principales

- textes obligatoires : `@NotBlank` ;
- longueurs maximales : `@Size` ;
- adresse e-mail : `@Email` ;
- telephone tunisien de huit chiffres : `@Pattern` ;
- date d'inscription : `@NotNull` et `@PastOrPresent` ;
- tarif journalier : `@NotNull` et `@DecimalMin("0.0")` ;
- identifiant d'agence : `@NotNull` et `@Positive`.

La dependance `spring-boot-starter-validation` etait deja presente dans le
`pom.xml` et n'a donc pas ete dupliquee.

## Gestion centralisee des erreurs

`GlobalExceptionHandler` traite notamment :

- `MethodArgumentNotValidException` : erreur de validation, HTTP 400 ;
- `HttpMessageNotReadableException` : JSON absent ou mal forme, HTTP 400 ;
- `ResourceNotFoundException` : ressource absente, HTTP 404 ;
- `DataIntegrityViolationException` : contrainte SQL, HTTP 409.

Exemple de reponse de validation :

```json
{
  "statut": 400,
  "message": "Les donnees envoyees sont invalides",
  "horodatage": "2026-11-17T18:00:00",
  "erreurs": {
    "email": "Le format de l'e-mail est invalide",
    "telephone": "Le telephone doit contenir exactement 8 chiffres"
  }
}
```

## Captures d'ecran conseillees

1. Swagger : section `Schemas` montrant un Request DTO et un Response DTO.
2. POST valide : code 201 et reponse ne contenant que le Response DTO.
3. POST invalide : code 400 et objet `erreurs` contenant les messages par champ.
4. GET : code 200 et liste de Response DTO.
5. Vehicule : reponse contenant `agenceId` et `agenceNom`, sans objet JPA imbrique.
