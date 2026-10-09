# Atelier 3 - Repository et services

## Choix de l'interface Repository

Les neuf repositories AutoLoc étendent `JpaRepository<Entite, Long>`.
`JpaRepository` a été retenue car elle fournit :

- le CRUD complet de `CrudRepository` ;
- des résultats sous forme de `List` via `ListCrudRepository` ;
- le tri et la pagination ;
- les opérations JPA telles que `flush()` et `saveAndFlush()`.

| Entité | Repository | Interface étendue |
|---|---|---|
| Agence | `IAgenceRepository` | `JpaRepository<Agence, Long>` |
| Client | `IClientRepository` | `JpaRepository<Client, Long>` |
| Contrat | `IContratRepository` | `JpaRepository<Contrat, Long>` |
| Employe | `IEmployeRepository` | `JpaRepository<Employe, Long>` |
| Equipement | `IEquipementRepository` | `JpaRepository<Equipement, Long>` |
| Maintenance | `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` |
| Paiement | `IPaiementRepository` | `JpaRepository<Paiement, Long>` |
| Reservation | `IReservationRepository` | `JpaRepository<Reservation, Long>` |
| Vehicule | `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` |

Les méthodes `findAll()` renvoient ainsi une `List`, contrairement à
`CrudRepository` qui renvoie un `Iterable`. Les suppressions en lot de
`JpaRepository` ne doivent pas être utilisées pour `Contrat`, car elles
contournent le contexte de persistance et donc la cascade et
`orphanRemoval` vers les paiements.

## Couche service

Chaque entité possède une interface `I...Service` et une classe
d'implémentation. Les opérations disponibles sont :

- `create` ;
- `findById` avec un retour `Optional` ;
- `findAll` ;
- `update` ;
- `deleteById`.

Les CRUD de `Contrat` et `Vehicule` sont écrits explicitement. Les sept
autres services réutilisent `AbstractCrudService` pour éviter de dupliquer
le même code tout en conservant une interface et une implémentation propres
à chaque entité.

## Analyse qualité

| Point contrôlé | Règle / explication | Correction appliquée |
|---|---|---|
| Injection par champ | Une dépendance injectée dans un champ est difficile à tester et peut rester non initialisée. | Injection par constructeur et champs `final` dans les services. |
| Lombok `@Data` sur une entité | `equals`, `hashCode` et `toString` générés sur les associations bidirectionnelles peuvent provoquer des cycles. | Conservation d'annotations Lombok ciblées : `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`. |
| Code CRUD dupliqué | Répéter le même CRUD dans neuf classes augmente le coût de maintenance et le risque d'incohérences. | Extraction du comportement commun dans `ICrudService` et `AbstractCrudService`; CRUD explicite conservé pour `Contrat` et `Vehicule`. |

## Vérification

- L'application conserve `spring.jpa.hibernate.ddl-auto=update`.
- Spring Data détecte neuf interfaces repository au démarrage.
- Les tests d'intégration vérifient la détection des neuf repositories et
  le CRUD complet de `Contrat` et `Vehicule`.
