package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
public class AgenceServiceImpl extends AbstractCrudService<Agence> implements IAgenceService {

    public AgenceServiceImpl(IAgenceRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Agence agence, Long id) {
        agence.setIdAgence(id);
    }

    @Override
    protected String entityName() {
        return "Agence";
    }
}
