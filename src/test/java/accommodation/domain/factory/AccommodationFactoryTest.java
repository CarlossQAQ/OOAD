package accommodation.domain.factory;

import org.junit.jupiter.api.*;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🏭  Factory + Builder 组合测试")
class AccommodationFactoryTest {

    @BeforeEach
    void before() {
        System.out.println("\n========= 开始执行 AccommodationFactoryTest =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= 结束 =========\n");
    }

    @Test
    @DisplayName("StandardAccommodationFactory 应生成标准间")
    void standardFactoryCreatesStandardRoom() {
        AccommodationFactory factory = new StandardAccommodationFactory();
        Accommodation room = factory.create(101);

        System.out.println("生成房间: " + room);

        assertAll("标准间属性校验",
                () -> assertEquals(101, room.getNumber(), "房号应为 101"),
                () -> assertEquals(RoomType.STANDARD, room.getType(), "房型应为 STANDARD"),
                () -> assertEquals(BigDecimal.valueOf(700), room.getPricePerMonth(), "价格应为 700")
        );
    }

    @Test
    @DisplayName("SuperiorAccommodationFactory 应生成高级间")
    void superiorFactoryCreatesSuperiorRoom() {
        AccommodationFactory factory = new SuperiorAccommodationFactory();
        Accommodation room = factory.create(202);

        System.out.println("生成房间: " + room);

        assertAll("高级间属性校验",
                () -> assertEquals(RoomType.SUPERIOR, room.getType(), "房型应为 SUPERIOR"),
                () -> assertEquals(BigDecimal.valueOf(950), room.getPricePerMonth(), "价格应为 950")
        );
    }
}
