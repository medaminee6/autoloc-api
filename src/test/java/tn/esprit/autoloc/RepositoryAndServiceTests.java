package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RepositoryAndServiceTests {

    @Autowired
    private List<JpaRepository<?, ?>> repositories;

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Autowired
    private IVehiculeService vehiculeService;

    @Autowired
    private IContratService contratService;

    @Test
    void springDiscoversNineJpaRepositories() {
        assertThat(repositories).hasSize(9);
    }

    @Test
    void vehiculeCrudIsComplete() {
        Agence agence = createAgence();
        VehiculeRequestDto vehiculeDto = createVehiculeDto(agence, "AT3-VEH-001", "206");

        VehiculeResponseDto created = vehiculeService.create(vehiculeDto);
        assertThat(created.idVehicule()).isNotNull();
        assertThat(vehiculeService.findById(created.idVehicule()).idVehicule())
                .isEqualTo(created.idVehicule());

        VehiculeRequestDto updateDto = createVehiculeDto(agence, "AT3-VEH-001", "208");
        VehiculeResponseDto updated = vehiculeService.update(created.idVehicule(), updateDto);
        assertThat(updated.modele()).isEqualTo("208");
        assertThat(vehiculeService.findAll()).extracting(VehiculeResponseDto::idVehicule)
                .contains(created.idVehicule());

        Long vehiculeId = created.idVehicule();
        vehiculeService.deleteById(vehiculeId);
        assertThatThrownBy(() -> vehiculeService.findById(vehiculeId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void contratCrudIsComplete() {
        Agence agence = createAgence();
        VehiculeResponseDto vehiculeDto = vehiculeService.create(
                createVehiculeDto(agence, "AT3-CON-001", "206"));
        Vehicule vehicule = vehiculeRepository.findById(vehiculeDto.idVehicule()).orElseThrow();
        Client client = createClient();

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 10, 10));
        reservation.setDateFin(LocalDate.of(2026, 10, 12));
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        reservation = reservationRepository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.of(2026, 10, 9));
        contrat.setMontantTotal(new BigDecimal("240.00"));
        contrat.setValide(true);
        contrat.setReservation(reservation);

        Contrat created = contratService.create(contrat);
        assertThat(created.getIdContrat()).isNotNull();
        assertThat(contratService.findById(created.getIdContrat()).getIdContrat())
                .isEqualTo(created.getIdContrat());

        created.setMontantTotal(new BigDecimal("260.00"));
        Contrat updated = contratService.update(created.getIdContrat(), created);
        assertThat(updated.getMontantTotal()).isEqualByComparingTo("260.00");
        assertThat(contratService.findAll()).extracting(Contrat::getIdContrat)
                .contains(created.getIdContrat());

        Long contratId = created.getIdContrat();
        contratService.deleteById(contratId);
        assertThatThrownBy(() -> contratService.findById(contratId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private Agence createAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Atelier 3");
        agence.setVille("Tunis");
        agence.setAdresse("ESPRIT");
        agence.setTelephone("70000000");
        return agenceRepository.save(agence);
    }

    private Client createClient() {
        Client client = new Client();
        client.setNom("Test");
        client.setPrenom("Atelier3");
        client.setEmail("atelier3@example.test");
        client.setTelephone("71000000");
        client.setNumPermis("PERMIS-AT3");
        client.setDateInscription(LocalDate.of(2026, 10, 9));
        return clientRepository.save(client);
    }

    private VehiculeRequestDto createVehiculeDto(
            Agence agence,
            String immatriculation,
            String modele) {
        return new VehiculeRequestDto(
                immatriculation,
                "Peugeot",
                modele,
                CategorieVehicule.CITADINE,
                new BigDecimal("120.00"),
                StatutVehicule.DISPONIBLE,
                agence.getIdAgence());
    }
}
