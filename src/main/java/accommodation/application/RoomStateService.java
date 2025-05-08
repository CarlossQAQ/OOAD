package accommodation.application;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.Student;
import accommodation.domain.state.StateContext;
import accommodation.domain.state.StateContextFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing room states
 */
public class RoomStateService {
    
    private final Map<Integer, StateContext> roomContexts = new HashMap<>();
    
    /**
     * Register an accommodation with the state service
     * @param accommodation The accommodation to register
     * @return The created state context
     */
    public StateContext registerRoom(Accommodation accommodation) {
        StateContext context = StateContextFactory.createContext(accommodation);
        roomContexts.put(accommodation.getNumber(), context);
        return context;
    }
    
    /**
     * Get the state context for a room number
     * @param roomNumber The room number
     * @return Optional containing the context if found
     */
    public Optional<StateContext> getRoom(int roomNumber) {
        return Optional.ofNullable(roomContexts.get(roomNumber));
    }
    
    /**
     * Assign a room to a student
     * @param roomNumber The room number
     * @param student The student to assign
     * @return true if successful, false otherwise
     */
    public boolean assignRoom(int roomNumber, Student student) {
        return getRoom(roomNumber)
                .map(context -> context.assign(student))
                .orElse(false);
    }
    
    /**
     * Vacate a room
     * @param roomNumber The room number
     * @return true if successful, false otherwise
     */
    public boolean vacateRoom(int roomNumber) {
        return getRoom(roomNumber)
                .map(StateContext::vacate)
                .orElse(false);
    }
    
    /**
     * Request maintenance for a room
     * @param roomNumber The room number
     * @return true if successful, false otherwise
     */
    public boolean requestMaintenance(int roomNumber) {
        return getRoom(roomNumber)
                .map(StateContext::requestMaintenance)
                .orElse(false);
    }
    
    /**
     * Complete maintenance for a room
     * @param roomNumber The room number
     * @return true if successful, false otherwise
     */
    public boolean completeMaintenance(int roomNumber) {
        return getRoom(roomNumber)
                .map(StateContext::completeMaintenance)
                .orElse(false);
    }
    
    /**
     * Get the state name for a room
     * @param roomNumber The room number
     * @return The state name or "UNKNOWN" if room not found
     */
    public String getRoomState(int roomNumber) {
        return getRoom(roomNumber)
                .map(StateContext::getStateName)
                .orElse("UNKNOWN");
    }
    
    /**
     * Get the assigned student for a room
     * @param roomNumber The room number
     * @return Optional containing the student if assigned
     */
    public Optional<Student> getAssignedStudent(int roomNumber) {
        return getRoom(roomNumber)
                .map(StateContext::getAssignedStudent);
    }
    
    /**
     * Get all room contexts
     * @return Map of room numbers to state contexts
     */
    public Map<Integer, StateContext> getAllRooms() {
        return new HashMap<>(roomContexts);
    }
} 