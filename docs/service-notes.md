# Atelier 4 - IoC, injection de dependances et couche Service

## A. Modes d'injection compares

| Critere | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dependance visible dans la signature | Oui | Non |
| Objet utilisable avec `new` hors de Spring | Oui, en fournissant la dependance | Risque de dependance `null` |
| Objet toujours complet apres construction | Oui | Non |
| SonarQube for IDE | Recommandee | Signalee par la regle S6813 |

AutoLoc retient l'injection par constructeur. `@RequiredArgsConstructor`
genere le constructeur des champs `final`; avec un constructeur unique,
`@Autowired` est inutile.

## B. Services

| Service | Dependance injectee | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Service volontairement limite a la lecture |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Dependance obligatoire, finale et typee par interface |

Les neuf implementations sont annotees `@Service` et
`@RequiredArgsConstructor`. Aucun service ne contient d'etat modifiable et
aucun repository n'est cree avec `new`.

Les neuf controleurs sont annotes `@RestController` et
`@RequiredArgsConstructor`. Chacun depend de l'interface de son service et
jamais du repository. Les routes declarees sont `/api/agences`,
`/api/employes`, `/api/vehicules`, `/api/equipements`, `/api/clients`,
`/api/reservations`, `/api/contrats`, `/api/paiements` et
`/api/maintenances`. Les operations HTTP seront ajoutees a l'Atelier 5.

## C. Messages d'erreur du conteneur

### Message 1 - aucun bean `IContratService`

Les deux causes les plus probables sont l'absence de `@Service` sur
`ContratServiceImpl`, ou une classe placee hors du package racine
`tn.esprit.autoloc` et donc hors du component scan. La correction consiste a
ajouter `@Service` et a placer la classe dans `tn.esprit.autoloc.service`.

### Message 2 - deux implementations du meme type

Spring trouve `emailNotificateur` et `smsNotificateur`. Deux corrections sont
possibles : placer `@Primary` sur la classe choisie par defaut, ou placer
`@Qualifier("emailNotificateur")` sur le parametre du constructeur du
consommateur.

### Message 3 - dependance circulaire

`ClientServiceImpl` et `ReservationServiceImpl` ne doivent pas s'injecter
mutuellement. L'injection par constructeur revele le cycle parce qu'aucun des
deux objets ne peut etre construit avant l'autre. Il faut extraire la logique
commune dans un troisieme service ou reorganiser la responsabilite pour rendre
la dependance unidirectionnelle.

## D. Anomalies SonarQube for IDE

| Regle / point | Explication | Correction apportee |
|---|---|---|
| S6813 - injection par attribut | La dependance est cachee, non finale et peut etre `null` hors du conteneur. | Injection par constructeur avec champ `final` et `@RequiredArgsConstructor`. |
| S112 - exception generique | `RuntimeException` ne decrit pas l'erreur metier. | Creation et utilisation de `ResourceNotFoundException`. |
| `@Data` sur les entites | Les associations bidirectionnelles peuvent provoquer des cycles dans `equals`, `hashCode` et `toString`. | Annotations Lombok ciblees (`@Getter`, `@Setter`, constructeurs). |

## E. Questions de comprehension

1. Il n'existe aucun `new ContratServiceImpl` dans le projet. Le conteneur
   Spring detecte `@Service`, appelle le constructeur genere par Lombok et
   cree le bean.
2. Un controleur depend de `IContratService` pour rester couple a une
   abstraction et permettre le remplacement ou le test de l'implementation.
3. Un service singleton est partage par plusieurs requetes et threads. Un
   champ tel que `contratCourant` pourrait etre ecrase par une autre requete
   et provoquer un traitement sur le mauvais contrat.
4. `update` charge l'entite existante pour conserver l'identifiant et les
   associations non modifiees. Sauvegarder directement l'objet recu pourrait
   remplacer des colonnes par `null` et vider une collection avec
   `orphanRemoval`.
5. `@Component` rend une classe detectee par le component scan, tandis que
   `@Bean` declare explicitement un objet dans une classe de configuration.
   `@Primary` choisit un bean par defaut, tandis que `@Qualifier` selectionne
   explicitement un bean au point d'injection. `IPaiementService` est en
   lecture seule parce que les paiements sont crees ou retires via `Contrat`
   afin de respecter la composition, la cascade et `orphanRemoval`.

## Regles metier implementees

- agence : nom obligatoire ;
- employe : nom et role obligatoires ;
- vehicule : tarif journalier non negatif ;
- equipement : libelle obligatoire (le modele ne contient pas de quantite) ;
- client : nom et e-mail obligatoires ;
- reservation : date de fin posterieure ou egale a la date de debut ;
- contrat : montant total non negatif ;
- paiement : lecture seule ;
- maintenance : date de fin posterieure ou egale a la date de debut (le modele
  ne contient pas de cout).
