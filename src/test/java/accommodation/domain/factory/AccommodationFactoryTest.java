package accommodation.domain.factory;

import org.junit.jupiter.api.*;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("��  Factory + Builder Combination Test")
class AccommodationFactoryTest {

    @BeforeEach
    void before() {
        System.out.println("\n========= Starting AccommodationFactoryTest =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= End =========\n");
    }

    @Test
    @DisplayName("StandardAccommodationFactory should create standard room")
    void standardFactoryCreatesStandardRoom() {
        AccommodationFactory factory = new StandardAccommodationFactory();
        Accommodation room = factory.create(101);

        System.out.println("Room created: " + room);

        assertAll("Standard room property validation",
                () -> assertEquals(101, room.getNumber(), "Room number should be 101"),
                () -> assertEquals(RoomType.STANDARD, room.getType(), "Room type should be STANDARD"),
                () -> assertEquals(BigDecimal.valueOf(700), room.getPricePerMonth(), "Price should be 700")
        );
    }

    @Test
    @DisplayName("SuperiorAccommodationFactory should create superior room")
    void superiorFactoryCreatesSuperiorRoom() {
        AccommodationFactory factory = new SuperiorAccommodationFactory();
        Accommodation room = factory.create(202);

        System.out.println("Room created: " + room);

        assertAll("Superior room property validation",
                () -> assertEquals(RoomType.SUPERIOR, room.getType(), "Room type should be SUPERIOR"),
                () -> assertEquals(BigDecimal.valueOf(950), room.getPricePerMonth(), "Price should be 950")
        );
    }
}
