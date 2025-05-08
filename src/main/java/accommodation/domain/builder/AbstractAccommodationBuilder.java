package accommodation.domain.factory;

import java.math.BigDecimal;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;


abstract class AbstractAccommodationBuilder implements AccommodationBuilder {
    protected int number;
    protected RoomType type;
    protected BigDecimal price;

    @Override public AccommodationBuilder number(int number) {
        this.number = number; return this;
    }

    @Override public AccommodationBuilder type(RoomType type) {
        this.type = type; return this;
    }

    @Override public AccommodationBuilder price(BigDecimal price) {
        this.price = price; return this;
    }

    @Override public Accommodation build() {
        return new Accommodation(number, type, price);
    }
}