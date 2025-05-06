package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** 具体工厂：标准间 */
public final class StandardAccommodationFactory implements AccommodationFactory {
    @Override public Accommodation create(int roomNumber) {
        return new StandardRoomBuilder().number(roomNumber).build();
    }
}