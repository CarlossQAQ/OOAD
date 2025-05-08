package accommodation.domain.factory;

import accommodation.domain.builder.SuperiorRoomBuilder;
import accommodation.domain.model.Accommodation;


public final class SuperiorAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new SuperiorRoomBuilder().number(roomNumber).build();
    }
}
