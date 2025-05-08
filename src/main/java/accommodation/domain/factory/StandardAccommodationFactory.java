package accommodation.domain.factory;

import accommodation.domain.builder.StandardRoomBuilder;
import accommodation.domain.model.Accommodation;


public final class StandardAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new StandardRoomBuilder().number(roomNumber).build();
    }
}