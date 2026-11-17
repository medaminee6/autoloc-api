package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceRequestDto;
import tn.esprit.autoloc.web.dto.AgenceResponseDto;
import tn.esprit.autoloc.web.mapper.AgenceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDto create(AgenceRequestDto agenceDto) {
        Agence agence = agenceMapper.toEntity(agenceDto);
        return agenceMapper.toResponseDto(agenceRepository.save(agence));
    }

    @Override
    public AgenceResponseDto findById(Long id) {
        return agenceMapper.toResponseDto(findEntityById(id));
    }

    @Override
    public List<AgenceResponseDto> findAll() {
        return agenceRepository.findAll().stream()
                .map(agenceMapper::toResponseDto)
                .toList();
    }

    @Override
    public AgenceResponseDto update(Long id, AgenceRequestDto agenceDto) {
        Agence agence = findEntityById(id);
        agenceMapper.updateEntity(agence, agenceDto);
        return agenceMapper.toResponseDto(agenceRepository.save(agence));
    }

    @Override
    public void deleteById(Long id) {
        Agence agence = findEntityById(id);
        agenceRepository.delete(agence);
    }

    private Agence findEntityById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
