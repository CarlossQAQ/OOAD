package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** 具体工厂：高级间 */
public final class SuperiorAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new SuperiorRoomBuilder().number(roomNumber).build();
    }
}
