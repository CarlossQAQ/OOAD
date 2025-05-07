package accommodation.domain.repository;

import accommodation.domain.model.Accommodation;

import java.util.Collection;
import java.util.Optional;

/** Persistence abstraction interface, demonstrates DIP */
public interface AccommodationRepository {
    void save(Accommodation accommodation);
    Optional<Accommodation> findByNumber(int roomNumber);
    Collection<Accommodation> findAll();
}