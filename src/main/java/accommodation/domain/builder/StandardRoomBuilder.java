package accommodation.domain.factory;

import java.math.BigDecimal;

import accommodation.domain.model.RoomType;


public final class StandardRoomBuilder extends AbstractAccommodationBuilder {
    public StandardRoomBuilder() {
        this.type = RoomType.STANDARD;
        this.price = BigDecimal.valueOf(700);
    }
}