package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
public class ClientServiceImpl extends AbstractCrudService<Client> implements IClientService {

    public ClientServiceImpl(IClientRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Client client, Long id) {
        client.setIdClient(id);
    }

    @Override
    protected String entityName() {
        return "Client";
    }
}
