package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;

import java.math.BigDecimal;

/** Builder 抽象：流式 API */
public interface AccommodationBuilder {

    AccommodationBuilder number(int number);

    AccommodationBuilder type(RoomType type);

    AccommodationBuilder price(BigDecimal price);

    Accommodation build();
}
