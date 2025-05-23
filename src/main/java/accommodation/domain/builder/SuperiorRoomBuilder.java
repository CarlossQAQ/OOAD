package accommodation.domain.builder;

import java.math.BigDecimal;

import accommodation.domain.model.RoomType;


public final class SuperiorRoomBuilder extends AbstractAccommodationBuilder {
    public SuperiorRoomBuilder() {
        this.type = RoomType.SUPERIOR;
        this.price = BigDecimal.valueOf(950);
    }
}
