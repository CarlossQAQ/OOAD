package accommodation.application;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.factory.SuperiorAccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import accommodation.domain.model.Student;
import accommodation.domain.state.StateContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🏨 Room State Service Test")
class RoomStateServiceTest {

    private RoomStateService service;
    private Accommodation standardRoom;
    private Accommodation superiorRoom;
    private Student student;

    @BeforeEach
    void setUp() {
        service = new RoomStateService();
        standardRoom = new StandardAccommodationFactory().create(101);
        superiorRoom = new SuperiorAccommodationFactory().create(202);
        student = new Student("S12345", "John Doe", "john@example.com");
        
        // Register rooms with service
        service.registerRoom(standardRoom);
        service.registerRoom(superiorRoom);
    }

    @Test
    @DisplayName("Should register rooms correctly")
    void shouldRegisterRoomsCorrectly() {
        Map<Integer, StateContext> allRooms = service.getAllRooms();
        assertEquals(2, allRooms.size(), "Should have 2 registered rooms");
        assertTrue(service.getRoom(101).isPresent(), "Should find room 101");
        assertTrue(service.getRoom(202).isPresent(), "Should find room 202");
    }

    @Test
    @DisplayName("Newly registered rooms should be in VACANT state")
    void newlyRegisteredRoomsShouldBeInVacantState() {
        assertEquals("VACANT", service.getRoomState(101), "Standard room should be VACANT");
        assertEquals("VACANT", service.getRoomState(202), "Superior room should be VACANT");
    }

    @Test
    @DisplayName("Should assign room to student")
    void shouldAssignRoomToStudent() {
        assertTrue(service.assignRoom(101, student), "Should successfully assign room");
        assertEquals("OCCUPIED", service.getRoomState(101), "Room should be OCCUPIED");
        
        Optional<Student> assignedStudent = service.getAssignedStudent(101);
        assertTrue(assignedStudent.isPresent(), "Should have assigned student");
        assertEquals(student, assignedStudent.get(), "Should be the correct student");
    }

    @Test
    @DisplayName("Should vacate assigned room")
    void shouldVacateAssignedRoom() {
        // First assign
        service.assignRoom(101, student);
        
        // Then vacate
        assertTrue(service.vacateRoom(101), "Should successfully vacate room");
        assertEquals("VACANT", service.getRoomState(101), "Room should be VACANT");
        assertFalse(service.getAssignedStudent(101).isPresent(), "Should not have assigned student");
    }

    @Test
    @DisplayName("Should handle maintenance requests")
    void shouldHandleMaintenanceRequests() {
        // Request maintenance for vacant room
        assertTrue(service.requestMaintenance(101), "Should successfully request maintenance");
        assertEquals("MAINTENANCE", service.getRoomState(101), "Room should be in MAINTENANCE state");
        
        // Cannot assign during maintenance
        assertFalse(service.assignRoom(101, student), "Should not be able to assign during maintenance");
        
        // Complete maintenance
        assertTrue(service.completeMaintenance(101), "Should successfully complete maintenance");
        assertEquals("VACANT", service.getRoomState(101), "Room should be VACANT after maintenance");
    }

    @Test
    @DisplayName("Should handle non-existent rooms")
    void shouldHandleNonExistentRooms() {
        assertEquals("UNKNOWN", service.getRoomState(999), "Should return UNKNOWN for non-existent room");
        assertFalse(service.assignRoom(999, student), "Should return false when assigning non-existent room");
        assertFalse(service.vacateRoom(999), "Should return false when vacating non-existent room");
        assertFalse(service.requestMaintenance(999), "Should return false when requesting maintenance for non-existent room");
        assertFalse(service.completeMaintenance(999), "Should return false when completing maintenance for non-existent room");
        assertFalse(service.getAssignedStudent(999).isPresent(), "Should return empty Optional for assigned student in non-existent room");
    }

    @Test
    @DisplayName("Should handle invalid state transitions")
    void shouldHandleInvalidStateTransitions() {
        // Assign a room
        service.assignRoom(101, student);
        
        // Try to request maintenance for occupied room
        assertFalse(service.requestMaintenance(101), "Should not allow maintenance for occupied room");
        assertEquals("OCCUPIED", service.getRoomState(101), "Room should remain OCCUPIED");
        
        // Put another room in maintenance
        service.requestMaintenance(202);
        
        // Try to vacate a room in maintenance
        assertFalse(service.vacateRoom(202), "Should not allow vacating a room in maintenance");
        assertEquals("MAINTENANCE", service.getRoomState(202), "Room should remain in MAINTENANCE");
    }

    @Test
    @DisplayName("Should handle complete lifecycle")
    void shouldHandleCompleteLifecycle() {
        // Initial state
        assertEquals("VACANT", service.getRoomState(101));
        
        // Assign room
        assertTrue(service.assignRoom(101, student));
        assertEquals("OCCUPIED", service.getRoomState(101));
        assertTrue(service.getAssignedStudent(101).isPresent());
        
        // Vacate room
        assertTrue(service.vacateRoom(101));
        assertEquals("VACANT", service.getRoomState(101));
        assertFalse(service.getAssignedStudent(101).isPresent());
        
        // Request maintenance
        assertTrue(service.requestMaintenance(101));
        assertEquals("MAINTENANCE", service.getRoomState(101));
        
        // Complete maintenance
        assertTrue(service.completeMaintenance(101));
        assertEquals("VACANT", service.getRoomState(101));
        
        // Register new room
        Accommodation newRoom = new Accommodation(303, RoomType.SUPERIOR, BigDecimal.valueOf(950));
        service.registerRoom(newRoom);
        assertEquals("VACANT", service.getRoomState(303));
    }
} 