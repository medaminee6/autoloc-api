package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends AbstractCrudService<Maintenance> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Maintenance maintenance, Long id) {
        maintenance.setIdMaintenance(id);
    }

    @Override
    protected String entityName() {
        return "Maintenance";
    }
}
