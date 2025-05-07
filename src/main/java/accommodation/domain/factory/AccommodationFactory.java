package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** Factory Method top-level interface */
@FunctionalInterface
public interface AccommodationFactory {
    Accommodation create(int roomNumber);
}