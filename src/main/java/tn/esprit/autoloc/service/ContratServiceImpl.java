package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    public ContratServiceImpl(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    @Override
    @Transactional
    public Contrat create(Contrat contrat) {
        contrat.setIdContrat(null);
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public Contrat update(Long id, Contrat contrat) {
        if (!contratRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable avec l'id " + id);
        }
        contrat.setIdContrat(id);
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> findById(Long id) {
        return contratRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        contratRepository.deleteById(id);
    }
}
