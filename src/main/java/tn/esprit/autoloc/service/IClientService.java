package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

import java.util.List;

public interface IClientService {

    ClientResponseDto create(ClientRequestDto clientDto);

    ClientResponseDto findById(Long id);

    List<ClientResponseDto> findAll();

    ClientResponseDto update(Long id, ClientRequestDto clientDto);

    void deleteById(Long id);
}
