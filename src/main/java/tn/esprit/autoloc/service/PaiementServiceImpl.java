package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
public class PaiementServiceImpl extends AbstractCrudService<Paiement> implements IPaiementService {

    public PaiementServiceImpl(IPaiementRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Paiement paiement, Long id) {
        paiement.setIdPaiement(id);
    }

    @Override
    protected String entityName() {
        return "Paiement";
    }
}
