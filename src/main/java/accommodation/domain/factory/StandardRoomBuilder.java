package accommodation.domain.factory;

import accommodation.domain.factory.AbstractAccommodationBuilder;
import accommodation.domain.model.RoomType;

import java.math.BigDecimal;

/** Standard Room Builder */
public final class StandardRoomBuilder extends AbstractAccommodationBuilder {
    public StandardRoomBuilder() {
        this.type = RoomType.STANDARD;
        this.price = BigDecimal.valueOf(700);
    }
}