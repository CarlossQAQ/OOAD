package accommodation.application;

import accommodation.domain.factory.AccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.domain.singleton.HallRegistry;

import java.util.Collection;

/**
 * Use case level service: Coordinates Factory + Repository
 */
public interface AccommodationService {

    Accommodation createAccommodation(int roomNumber);

    Collection<Accommodation> listAll();

}