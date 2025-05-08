package accommodation.domain.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("📋 Accommodation Domain Model Test")
class AccommodationTest {

    @Test
    @DisplayName("Constructor should create valid accommodation")
    void constructorCreatesValidAccommodation() {
        Accommodation accommodation = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        
        assertAll("Accommodation properties",
            () -> assertEquals(101, accommodation.getNumber(), "Room number should be 101"),
            () -> assertEquals(RoomType.STANDARD, accommodation.getType(), "Room type should be STANDARD"),
            () -> assertEquals(BigDecimal.valueOf(700), accommodation.getPricePerMonth(), "Price should be 700")
        );
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("Constructor should throw exception for non-positive room numbers")
    void constructorThrowsExceptionForNonPositiveRoomNumbers(int invalidRoomNumber) {
        assertThrows(IllegalArgumentException.class, () -> 
            new Accommodation(invalidRoomNumber, RoomType.STANDARD, BigDecimal.valueOf(700)),
            "Should throw exception for non-positive room number"
        );
    }
    
    @Test
    @DisplayName("Constructor should throw exception for null room type")
    void constructorThrowsExceptionForNullRoomType() {
        assertThrows(NullPointerException.class, () -> 
            new Accommodation(101, null, BigDecimal.valueOf(700)),
            "Should throw exception for null room type"
        );
    }
    
    @Test
    @DisplayName("Constructor should throw exception for null price")
    void constructorThrowsExceptionForNullPrice() {
        assertThrows(NullPointerException.class, () -> 
            new Accommodation(101, RoomType.STANDARD, null),
            "Should throw exception for null price"
        );
    }
    
    @Test
    @DisplayName("equals() should return true for equal accommodations")
    void equalsReturnsTrueForEqualAccommodations() {
        Accommodation accommodation1 = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        Accommodation accommodation2 = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        
        assertEquals(accommodation1, accommodation2, "Equal accommodations should be equal");
        assertEquals(accommodation1.hashCode(), accommodation2.hashCode(), "Equal accommodations should have equal hash codes");
    }
    
    @Test
    @DisplayName("equals() should return false for different accommodations")
    void equalsReturnsFalseForDifferentAccommodations() {
        Accommodation accommodation1 = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        Accommodation accommodation2 = new Accommodation(102, RoomType.STANDARD, BigDecimal.valueOf(700));
        Accommodation accommodation3 = new Accommodation(101, RoomType.SUPERIOR, BigDecimal.valueOf(700));
        Accommodation accommodation4 = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(750));
        
        assertNotEquals(accommodation1, accommodation2, "Different room numbers should not be equal");
        assertNotEquals(accommodation1, accommodation3, "Different room types should not be equal");
        assertNotEquals(accommodation1, accommodation4, "Different prices should not be equal");
    }
    
    @Test
    @DisplayName("toString() should return proper string representation")
    void toStringReturnsProperStringRepresentation() {
        Accommodation accommodation = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        String result = accommodation.toString();
        
        assertTrue(result.contains("101"), "toString should contain room number");
        assertTrue(result.contains("STANDARD"), "toString should contain room type");
        assertTrue(result.contains("700"), "toString should contain price");
    }
} 