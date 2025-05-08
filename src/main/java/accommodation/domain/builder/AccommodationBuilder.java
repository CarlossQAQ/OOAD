package accommodation.domain.builder;

import java.math.BigDecimal;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;


public interface AccommodationBuilder {

    AccommodationBuilder number(int number);

    AccommodationBuilder type(RoomType type);

    AccommodationBuilder price(BigDecimal price);

    Accommodation build();
}
