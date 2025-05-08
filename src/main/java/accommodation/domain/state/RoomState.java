package accommodation.domain.state;

import accommodation.domain.model.Student;

/**
 * State Pattern: Base interface for room states
 * Defines all possible transitions and operations in different states
 */
public interface RoomState {
    /**
     * Assign room to a student
     * @param context The stateful room context
     * @param student The student to assign
     * @return true if operation successful, false otherwise
     */
    boolean assign(StateContext context, Student student);
    
    /**
     * Vacate the room
     * @param context The stateful room context
     * @return true if operation successful, false otherwise
     */
    boolean vacate(StateContext context);
    
    /**
     * Request maintenance for the room
     * @param context The stateful room context
     * @return true if operation successful, false otherwise
     */
    boolean requestMaintenance(StateContext context);
    
    /**
     * Mark room as available after maintenance
     * @param context The stateful room context
     * @return true if operation successful, false otherwise
     */
    boolean completeMaintenance(StateContext context);
    
    /**
     * Get the name of current state
     * @return State name
     */
    String getStateName();
} 