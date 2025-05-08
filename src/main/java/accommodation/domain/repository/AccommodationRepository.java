package accommodation.domain.repository;

import java.util.Collection;
import java.util.Optional;

import accommodation.domain.model.Accommodation;

public interface AccommodationRepository {
    void save(Accommodation accommodation);
    Optional<Accommodation> findByNumber(int roomNumber);
    Collection<Accommodation> findAll();
}