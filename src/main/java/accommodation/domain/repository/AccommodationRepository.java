package accommodation.domain.repository;

import accommodation.domain.model.Accommodation;

import java.util.Collection;
import java.util.Optional;

/** 持久化抽象接口，体现 DIP */
public interface AccommodationRepository {
    void save(Accommodation accommodation);
    Optional<Accommodation> findByNumber(int roomNumber);
    Collection<Accommodation> findAll();
}