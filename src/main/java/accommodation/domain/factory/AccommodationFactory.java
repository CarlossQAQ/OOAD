package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;


@FunctionalInterface
public interface AccommodationFactory {
    Accommodation create(int roomNumber);
}