package accommodation.infrastructure.repository;

import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** In-memory implementation, convenient for testing */
public final class InMemoryAccommodationRepository implements AccommodationRepository {
    private final ConcurrentHashMap<Integer, Accommodation> store = new ConcurrentHashMap<>();

    @Override public void save(Accommodation accommodation) {
        store.put(accommodation.getNumber(), accommodation);
    }

    @Override public Optional<Accommodation> findByNumber(int roomNumber) {
        return Optional.ofNullable(store.get(roomNumber));
    }

    @Override public Collection<Accommodation> findAll() {
        return store.values();
    }
}
