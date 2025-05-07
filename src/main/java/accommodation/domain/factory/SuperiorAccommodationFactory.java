package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** Concrete Factory: Superior Room */
public final class SuperiorAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new SuperiorRoomBuilder().number(roomNumber).build();
    }
}
