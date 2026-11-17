package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequestDto dto, Agence agence) {
        Vehicule vehicule = new Vehicule();
        updateEntity(vehicule, dto, agence);
        return vehicule;
    }

    public void updateEntity(Vehicule vehicule, VehiculeRequestDto dto, Agence agence) {
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setCategorie(dto.categorie());
        vehicule.setTarifJournalier(dto.tarifJournalier());
        vehicule.setStatut(dto.statut());
        vehicule.setAgence(agence);
    }

    public VehiculeResponseDto toResponseDto(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        return new VehiculeResponseDto(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agence == null ? null : agence.getIdAgence(),
                agence == null ? null : agence.getNom());
    }
}
