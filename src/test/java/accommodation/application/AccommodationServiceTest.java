package accommodation.application;


import accommodation.application.impl.AccommodationServiceImpl;
import accommodation.domain.factory.AccommodationFactory;
import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.factory.SuperiorAccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.domain.singleton.HallRegistry;
import accommodation.infrastructure.repository.InMemoryAccommodationRepository;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(" Application Service Test")
class AccommodationServiceTest {

    private AccommodationService standardService;
    private AccommodationService superiorService;
    private AccommodationRepository repo;

    @BeforeEach
    void setUp() {
        HallRegistry.resetForTest(); // Use the public test helper method
        repo = new InMemoryAccommodationRepository();
        standardService = new AccommodationServiceImpl(new StandardAccommodationFactory(), repo);
        superiorService = new AccommodationServiceImpl(new SuperiorAccommodationFactory(), repo);
        System.out.println("\n========= AccommodationServiceTest ready =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= End =========\n");
    }

    @Test
    @DisplayName("createAccommodation() should persist and register room")
    void createAccommodationPersistsAndRegisters() {
        Accommodation created = standardService.createAccommodation(401);

        System.out.println("Created and registered room: " + created);
        System.out.println("Repository current list: " + standardService.listAll());

        assertAll("Room persistence + query validation",
                () -> assertTrue(repo.findByNumber(401).isPresent(), "Repository should contain room 401"),
                () -> assertEquals(List.of(created), standardService.listAll().stream().toList(), "listAll() should return the room")
        );
    }
    
    @Test
    @DisplayName("findByNumber() should return room from registry")
    void findByNumberShouldReturnRoomFromRegistry() {
        // Create a room using the service
        Accommodation created = standardService.createAccommodation(501);
        
        // Test the findByNumber method on service implementation
        AccommodationServiceImpl serviceImpl = (AccommodationServiceImpl) standardService;
        Accommodation found = serviceImpl.findByNumber(501);
        
        // Verify
        assertEquals(created, found, "findByNumber should return the created room");
    }
    
    @Test
    @DisplayName("findByNumber() should return null for non-existent room")
    void findByNumberShouldReturnNullForNonExistentRoom() {
        AccommodationServiceImpl serviceImpl = (AccommodationServiceImpl) standardService;
        Accommodation found = serviceImpl.findByNumber(999);
        
        assertNull(found, "findByNumber should return null for non-existent room");
    }
    
    @Test
    @DisplayName("Service should work with different room factories")
    void serviceShouldWorkWithDifferentRoomFactories() {
        // Create rooms using different services with different factories
        Accommodation standardRoom = standardService.createAccommodation(601);
        Accommodation superiorRoom = superiorService.createAccommodation(602);
        
        // Verify room types
        assertEquals(RoomType.STANDARD, standardRoom.getType(), "Standard service should create STANDARD room");
        assertEquals(RoomType.SUPERIOR, superiorRoom.getType(), "Superior service should create SUPERIOR room");
        
        // Verify repository has both rooms
        Collection<Accommodation> allRooms = standardService.listAll();
        assertEquals(2, allRooms.size(), "Repository should contain 2 rooms");
        
        // Both services should see both rooms since they share the same repository
        assertEquals(2, superiorService.listAll().size(), "Both services should see the same rooms");
    }
    
    @Test
    @DisplayName("Service should handle overwriting rooms with same number")
    void serviceShouldHandleOverwritingRoomsWithSameNumber() {
        // Create a standard room
        Accommodation standardRoom = standardService.createAccommodation(701);
        
        // Create a superior room with same number
        Accommodation superiorRoom = superiorService.createAccommodation(701);
        
        // Verify repository only has one room (the superior one)
        Collection<Accommodation> allRooms = standardService.listAll();
        assertEquals(1, allRooms.size(), "Repository should contain 1 room");
        
        // Verify the room in repository is the superior one
        Optional<Accommodation> foundRoom = repo.findByNumber(701);
        assertTrue(foundRoom.isPresent(), "Room should exist in repository");
        assertEquals(RoomType.SUPERIOR, foundRoom.get().getType(), "Room should be SUPERIOR type");
        
        // Verify registry also has the superior room
        AccommodationServiceImpl serviceImpl = (AccommodationServiceImpl) standardService;
        Accommodation registryRoom = serviceImpl.findByNumber(701);
        assertEquals(RoomType.SUPERIOR, registryRoom.getType(), "Registry should have SUPERIOR room");
    }
    
    @Test
    @DisplayName("Integration test: create, find, and list rooms")
    void integrationTestCreateFindAndListRooms() {
        // Create rooms
        standardService.createAccommodation(801);
        standardService.createAccommodation(802);
        superiorService.createAccommodation(803);
        
        // Verify repository state
        Collection<Accommodation> allRooms = standardService.listAll();
        assertEquals(3, allRooms.size(), "Repository should contain 3 rooms");
        
        // Verify each room exists and has correct type
        Optional<Accommodation> room801 = repo.findByNumber(801);
        Optional<Accommodation> room803 = repo.findByNumber(803);
        
        assertTrue(room801.isPresent(), "Room 801 should exist");
        assertTrue(room803.isPresent(), "Room 803 should exist");
        
        assertEquals(RoomType.STANDARD, room801.get().getType(), "Room 801 should be STANDARD");
        assertEquals(RoomType.SUPERIOR, room803.get().getType(), "Room 803 should be SUPERIOR");
        
        // Verify registry access
        AccommodationServiceImpl serviceImpl = (AccommodationServiceImpl) standardService;
        Accommodation room802Registry = serviceImpl.findByNumber(802);
        assertNotNull(room802Registry, "Registry should contain room 802");
        assertEquals(802, room802Registry.getNumber(), "Room number should be 802");
    }
}
