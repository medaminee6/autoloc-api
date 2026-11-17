package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;

import java.util.List;

public interface IVehiculeService {

    VehiculeResponseDto create(VehiculeRequestDto vehiculeDto);

    VehiculeResponseDto findById(Long id);

    List<VehiculeResponseDto> findAll();

    VehiculeResponseDto update(Long id, VehiculeRequestDto vehiculeDto);

    void deleteById(Long id);
}
