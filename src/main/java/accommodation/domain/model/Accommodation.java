package accommodation.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 领域对象：宿舍房间（不可变）
 */
public final class Accommodation {
    private final int number;
    private final RoomType type;
    private final BigDecimal pricePerMonth;

    public Accommodation(int number, RoomType type, BigDecimal pricePerMonth) {
        if (number <= 0) {
            throw new IllegalArgumentException("room number must be positive");
        }
        this.number = number;
        this.type = Objects.requireNonNull(type);
        this.pricePerMonth = Objects.requireNonNull(pricePerMonth);
    }

    public int getNumber()               { return number; }
    public RoomType getType()            { return type; }
    public BigDecimal getPricePerMonth() { return pricePerMonth; }

    @Override public String toString() {
        return "Accommodation{" +
                "number=" + number +
                ", type=" + type +
                ", price=" + pricePerMonth +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o)  return true;
        if (!(o instanceof Accommodation that)) return false;
        return number == that.number && type == that.type &&
                pricePerMonth.compareTo(that.pricePerMonth) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(number, type, pricePerMonth);
    }
}
