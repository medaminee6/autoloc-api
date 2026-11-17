package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AgenceRequestDto;
import tn.esprit.autoloc.web.dto.AgenceResponseDto;

import java.util.List;

public interface IAgenceService {

    AgenceResponseDto create(AgenceRequestDto agenceDto);

    AgenceResponseDto findById(Long id);

    List<AgenceResponseDto> findAll();

    AgenceResponseDto update(Long id, AgenceRequestDto agenceDto);

    void deleteById(Long id);
}
