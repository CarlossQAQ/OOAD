package accommodation.domain.singleton;


import org.junit.jupiter.api.*;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.factory.SuperiorAccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔒  Singleton HallRegistry Test")
class HallRegistryTest {

    @BeforeEach
    void clearRegistry() {
        HallRegistry.resetForTest();   // Use the public test helper method
        System.out.println("\n========= Registry reset =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= End =========\n");
    }

    @Test
    @DisplayName("instance() should return the same object")
    void singletonReturnsSameInstance() {
        HallRegistry r1 = HallRegistry.instance();
        HallRegistry r2 = HallRegistry.instance();

        System.out.printf("Instance 1 hash=%d, Instance 2 hash=%d%n",
                System.identityHashCode(r1), System.identityHashCode(r2));

        assertSame(r1, r2, "Multiple calls should return the same instance");
    }

    @Test
    @DisplayName("After register() should be able to find() successfully")
    void registerAndRetrieveRoom() {
        Accommodation room = new StandardAccommodationFactory().create(301);
        HallRegistry.instance().register(room);

        System.out.println("Registered room: " + room);
        System.out.println("Current Registry content: " + HallRegistry.instance().all());

        assertEquals(room, HallRegistry.instance().find(301), "Should be able to find the registered room");
    }
    
    @Test
    @DisplayName("Registry should handle overwriting existing room with same number")
    void registryShouldHandleOverwritingExistingRoom() {
        // Register a standard room
        Accommodation standardRoom = new StandardAccommodationFactory().create(401);
        HallRegistry.instance().register(standardRoom);
        
        // Register a superior room with the same number
        Accommodation superiorRoom = new SuperiorAccommodationFactory().create(401);
        HallRegistry.instance().register(superiorRoom);
        
        // Check that the superior room replaced the standard room
        Accommodation found = HallRegistry.instance().find(401);
        assertEquals(superiorRoom, found, "Registry should contain the most recently registered room");
        assertEquals(RoomType.SUPERIOR, found.getType(), "Room type should be SUPERIOR (last registered)");
    }
    
    @Test
    @DisplayName("Registry should return null for non-existent room")
    void registryShouldReturnNullForNonExistentRoom() {
        assertNull(HallRegistry.instance().find(999), "Should return null for non-existent room number");
    }
    
    @Test
    @DisplayName("all() should return unmodifiable map")
    void allShouldReturnUnmodifiableMap() {
        HallRegistry registry = HallRegistry.instance();
        
        // Register some rooms
        registry.register(new Accommodation(501, RoomType.STANDARD, BigDecimal.valueOf(700)));
        registry.register(new Accommodation(502, RoomType.SUPERIOR, BigDecimal.valueOf(950)));
        
        Map<Integer, Accommodation> allRooms = registry.all();
        
        // Verify content
        assertEquals(2, allRooms.size(), "Map should contain 2 rooms");
        assertTrue(allRooms.containsKey(501), "Map should contain room 501");
        assertTrue(allRooms.containsKey(502), "Map should contain room 502");
        
        // Verify map is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> 
            allRooms.put(503, new Accommodation(503, RoomType.STANDARD, BigDecimal.valueOf(700))),
            "Map returned by all() should be unmodifiable"
        );
    }
    
    @Test
    @DisplayName("Registry should work with multiple room types")
    void registryShouldWorkWithMultipleRoomTypes() {
        HallRegistry registry = HallRegistry.instance();
        
        Accommodation standardRoom = new StandardAccommodationFactory().create(601);
        Accommodation superiorRoom = new SuperiorAccommodationFactory().create(602);
        
        registry.register(standardRoom);
        registry.register(superiorRoom);
        
        assertEquals(standardRoom, registry.find(601), "Should find standard room");
        assertEquals(superiorRoom, registry.find(602), "Should find superior room");
        assertEquals(2, registry.all().size(), "Registry should contain 2 rooms");
    }
}
