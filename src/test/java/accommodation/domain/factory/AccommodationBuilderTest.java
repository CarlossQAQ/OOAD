package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔨 Accommodation Builder Pattern Test")
class AccommodationBuilderTest {

    @Test
    @DisplayName("StandardRoomBuilder should create standard room with default values")
    void standardRoomBuilderCreatesStandardRoom() {
        StandardRoomBuilder builder = new StandardRoomBuilder();
        Accommodation room = builder.number(101).build();
        
        assertAll("Standard room defaults",
            () -> assertEquals(101, room.getNumber()),
            () -> assertEquals(RoomType.STANDARD, room.getType()),
            () -> assertEquals(BigDecimal.valueOf(700), room.getPricePerMonth())
        );
    }
    
    @Test
    @DisplayName("SuperiorRoomBuilder should create superior room with default values")
    void superiorRoomBuilderCreatesSuperiorRoom() {
        SuperiorRoomBuilder builder = new SuperiorRoomBuilder();
        Accommodation room = builder.number(201).build();
        
        assertAll("Superior room defaults",
            () -> assertEquals(201, room.getNumber()),
            () -> assertEquals(RoomType.SUPERIOR, room.getType()),
            () -> assertEquals(BigDecimal.valueOf(950), room.getPricePerMonth())
        );
    }
    
    @Test
    @DisplayName("Builder should allow overriding default values")
    void builderAllowsOverridingDefaults() {
        StandardRoomBuilder builder = new StandardRoomBuilder();
        BigDecimal customPrice = BigDecimal.valueOf(800);
        Accommodation room = builder
                .number(101)
                .price(customPrice)
                .build();
        
        assertEquals(customPrice, room.getPricePerMonth(), "Custom price should be applied");
    }
    
    @Test
    @DisplayName("Builder should support fluent API chaining")
    void builderSupportsFluentApiChaining() {
        SuperiorRoomBuilder builder = new SuperiorRoomBuilder();
        BigDecimal customPrice = BigDecimal.valueOf(1000);
        
        Accommodation room = builder
                .number(202)
                .type(RoomType.SUPERIOR) // redundant but testing method
                .price(customPrice)
                .build();
        
        assertAll("Fluent API chaining",
            () -> assertEquals(202, room.getNumber()),
            () -> assertEquals(RoomType.SUPERIOR, room.getType()),
            () -> assertEquals(customPrice, room.getPricePerMonth())
        );
    }
    
    @Test
    @DisplayName("Builder should create different instances with same values")
    void builderCreatesDifferentInstancesWithSameValues() {
        StandardRoomBuilder builder = new StandardRoomBuilder();
        Accommodation room1 = builder.number(101).build();
        Accommodation room2 = builder.number(101).build();
        
        assertNotSame(room1, room2, "Builder should create different instances");
        assertEquals(room1, room2, "But with equal content");
    }
    
    @Test
    @DisplayName("Builder should handle negative prices")
    void builderHandlesNegativePrices() {
        SuperiorRoomBuilder builder = new SuperiorRoomBuilder();
        Accommodation room = builder
                .number(301)
                .price(BigDecimal.valueOf(-100))
                .build();
        
        assertEquals(BigDecimal.valueOf(-100), room.getPricePerMonth(), 
                "Builder should accept negative prices (validation is not in builder)");
    }
} 