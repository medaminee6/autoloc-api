package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;
import tn.esprit.autoloc.web.mapper.ClientMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDto create(ClientRequestDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        return clientMapper.toResponseDto(clientRepository.save(client));
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return clientMapper.toResponseDto(findEntityById(id));
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponseDto)
                .toList();
    }

    @Override
    public ClientResponseDto update(Long id, ClientRequestDto clientDto) {
        Client client = findEntityById(id);
        clientMapper.updateEntity(client, clientDto);
        return clientMapper.toResponseDto(clientRepository.save(client));
    }

    @Override
    public void deleteById(Long id) {
        Client client = findEntityById(id);
        clientRepository.delete(client);
    }

    private Client findEntityById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
