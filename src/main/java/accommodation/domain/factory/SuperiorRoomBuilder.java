package accommodation.domain.factory;

import accommodation.domain.model.RoomType;

import java.math.BigDecimal;

/** Superior Room Builder */
public final class SuperiorRoomBuilder extends AbstractAccommodationBuilder {
    public SuperiorRoomBuilder() {
        this.type = RoomType.SUPERIOR;
        this.price = BigDecimal.valueOf(950);
    }
}
