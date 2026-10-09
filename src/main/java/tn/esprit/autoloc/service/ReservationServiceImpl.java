package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
public class ReservationServiceImpl extends AbstractCrudService<Reservation> implements IReservationService {

    public ReservationServiceImpl(IReservationRepository repository) {
        super(repository);
    }

    @Override
    protected void assignId(Reservation reservation, Long id) {
        reservation.setIdReservation(id);
    }

    @Override
    protected String entityName() {
        return "Reservation";
    }
}
