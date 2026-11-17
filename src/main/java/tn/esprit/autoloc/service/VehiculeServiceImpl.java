package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDto create(VehiculeRequestDto vehiculeDto) {
        Agence agence = resolveAgence(vehiculeDto.agenceId());
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDto, agence);
        return vehiculeMapper.toResponseDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeResponseDto findById(Long id) {
        return vehiculeMapper.toResponseDto(findEntityById(id));
    }

    @Override
    public List<VehiculeResponseDto> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toResponseDto)
                .toList();
    }

    @Override
    public VehiculeResponseDto update(Long id, VehiculeRequestDto vehiculeDto) {
        Vehicule vehicule = findEntityById(id);
        Agence agence = resolveAgence(vehiculeDto.agenceId());
        vehiculeMapper.updateEntity(vehicule, vehiculeDto, agence);
        return vehiculeMapper.toResponseDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public void deleteById(Long id) {
        Vehicule vehicule = findEntityById(id);
        vehiculeRepository.delete(vehicule);
    }

    private Vehicule findEntityById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Agence resolveAgence(Long agenceId) {
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
    }
}
