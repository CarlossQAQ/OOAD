package accommodation.domain.state;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import accommodation.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔄 Room State Pattern Test")
class RoomStateTest {

    private StateContext context;
    private Accommodation room;
    private Student student;
    private List<String> stateTransitions;

    @BeforeEach
    void setUp() {
        room = new Accommodation(101, RoomType.STANDARD, BigDecimal.valueOf(700));
        context = StateContextFactory.createContext(room);
        student = new Student("S12345", "John Doe", "john@example.com");
        
        // Track state transitions
        stateTransitions = new ArrayList<>();
        context.addStateChangeListener((ctx, oldState, newState) -> 
            stateTransitions.add(oldState.getStateName() + " -> " + newState.getStateName()));
    }

    @Test
    @DisplayName("Room should start in VACANT state")
    void roomShouldStartInVacantState() {
        assertEquals("VACANT", context.getStateName(), "Initial state should be VACANT");
        assertTrue(context.getCurrentState() instanceof VacantState, "Current state should be VacantState");
    }

    @Test
    @DisplayName("Vacant room should be assignable")
    void vacantRoomShouldBeAssignable() {
        assertTrue(context.assign(student), "Should be able to assign a vacant room");
        assertEquals("OCCUPIED", context.getStateName(), "State should change to OCCUPIED");
        assertEquals(student, context.getAssignedStudent(), "Assigned student should be set");
        assertEquals(1, stateTransitions.size(), "Should have one state transition");
        assertEquals("VACANT -> OCCUPIED", stateTransitions.get(0), "Should transition from VACANT to OCCUPIED");
    }

    @Test
    @DisplayName("Occupied room should not be assignable")
    void occupiedRoomShouldNotBeAssignable() {
        // First assign the room
        context.assign(student);
        
        // Try to assign again with another student
        Student anotherStudent = new Student("S67890", "Jane Smith", "jane@example.com");
        assertFalse(context.assign(anotherStudent), "Should not be able to assign an occupied room");
        assertEquals("OCCUPIED", context.getStateName(), "State should remain OCCUPIED");
        assertEquals(student, context.getAssignedStudent(), "Assigned student should not change");
        assertEquals(1, stateTransitions.size(), "Should still have only one state transition");
    }

    @Test
    @DisplayName("Occupied room should be vacatable")
    void occupiedRoomShouldBeVacatable() {
        // First assign the room
        context.assign(student);
        
        // Then vacate it
        assertTrue(context.vacate(), "Should be able to vacate an occupied room");
        assertEquals("VACANT", context.getStateName(), "State should change to VACANT");
        assertNull(context.getAssignedStudent(), "Assigned student should be null");
        assertEquals(2, stateTransitions.size(), "Should have two state transitions");
        assertEquals("OCCUPIED -> VACANT", stateTransitions.get(1), "Should transition from OCCUPIED to VACANT");
    }

    @Test
    @DisplayName("Vacant room can go to maintenance")
    void vacantRoomCanGoToMaintenance() {
        assertTrue(context.requestMaintenance(), "Should be able to request maintenance for a vacant room");
        assertEquals("MAINTENANCE", context.getStateName(), "State should change to MAINTENANCE");
        assertEquals(1, stateTransitions.size(), "Should have one state transition");
        assertEquals("VACANT -> MAINTENANCE", stateTransitions.get(0), "Should transition from VACANT to MAINTENANCE");
    }

    @Test
    @DisplayName("Occupied room cannot go to maintenance")
    void occupiedRoomCannotGoToMaintenance() {
        // First assign the room
        context.assign(student);
        
        // Try to request maintenance
        assertFalse(context.requestMaintenance(), "Should not be able to request maintenance for an occupied room");
        assertEquals("OCCUPIED", context.getStateName(), "State should remain OCCUPIED");
        assertEquals(1, stateTransitions.size(), "Should still have only one state transition");
    }

    @Test
    @DisplayName("Maintenance room cannot be assigned")
    void maintenanceRoomCannotBeAssigned() {
        // Put room in maintenance
        context.requestMaintenance();
        
        // Try to assign
        assertFalse(context.assign(student), "Should not be able to assign a room under maintenance");
        assertEquals("MAINTENANCE", context.getStateName(), "State should remain MAINTENANCE");
        assertNull(context.getAssignedStudent(), "Assigned student should remain null");
    }

    @Test
    @DisplayName("Maintenance can be completed")
    void maintenanceCanBeCompleted() {
        // Put room in maintenance
        context.requestMaintenance();
        
        // Complete maintenance
        assertTrue(context.completeMaintenance(), "Should be able to complete maintenance");
        assertEquals("VACANT", context.getStateName(), "State should change to VACANT");
        assertEquals(2, stateTransitions.size(), "Should have two state transitions");
        assertEquals("MAINTENANCE -> VACANT", stateTransitions.get(1), "Should transition from MAINTENANCE to VACANT");
    }

    @Test
    @DisplayName("Full lifecycle test")
    void fullLifecycleTest() {
        // Initial state
        assertEquals("VACANT", context.getStateName());
        
        // Assign to student
        assertTrue(context.assign(student));
        assertEquals("OCCUPIED", context.getStateName());
        
        // Vacate
        assertTrue(context.vacate());
        assertEquals("VACANT", context.getStateName());
        
        // Request maintenance
        assertTrue(context.requestMaintenance());
        assertEquals("MAINTENANCE", context.getStateName());
        
        // Complete maintenance
        assertTrue(context.completeMaintenance());
        assertEquals("VACANT", context.getStateName());
        
        // Assign again
        assertTrue(context.assign(student));
        assertEquals("OCCUPIED", context.getStateName());
        
        // Verify all transitions
        assertEquals(4, stateTransitions.size());
        assertEquals("VACANT -> OCCUPIED", stateTransitions.get(0));
        assertEquals("OCCUPIED -> VACANT", stateTransitions.get(1));
        assertEquals("VACANT -> MAINTENANCE", stateTransitions.get(2));
        assertEquals("MAINTENANCE -> VACANT", stateTransitions.get(3));
    }
} 