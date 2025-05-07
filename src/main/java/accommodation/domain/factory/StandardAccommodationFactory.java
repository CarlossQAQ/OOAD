package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** Concrete Factory: Standard Room */
public final class StandardAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new StandardRoomBuilder().number(roomNumber).build();
    }
}