package accommodation.application.impl;

import accommodation.application.AccommodationService;
import accommodation.domain.factory.AccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.domain.singleton.HallRegistry;

import java.util.Collection;

public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationFactory factory;
    private final AccommodationRepository repository;

    public AccommodationServiceImpl(AccommodationFactory factory,
                                AccommodationRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    /** Creates room and registers it with repository and singleton Registry */
    public Accommodation createAccommodation(int roomNumber) {
        Accommodation acc = factory.create(roomNumber);
        repository.save(acc);
        HallRegistry.instance().register(acc);
        return acc;
    }

    public Collection<Accommodation> listAll() {
        return repository.findAll();
    }

    public Accommodation findByNumber(int roomNumber) {
        return HallRegistry.instance().find(roomNumber);
    }
}
