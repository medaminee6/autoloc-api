package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
public class EmployeServiceImpl extends AbstractCrudService<Employe> implements IEmployeService {

    public EmployeServiceImpl(IEmployeRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Employe employe, Long id) {
        employe.setIdEmploye(id);
    }

    @Override
    protected String entityName() {
        return "Employe";
    }
}
