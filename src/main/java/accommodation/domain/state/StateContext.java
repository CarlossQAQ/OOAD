package accommodation.domain.state;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * State Pattern: Context class that maintains current state
 * and delegates operations to the state object
 */
public class StateContext {
    private RoomState currentState;
    private final Accommodation accommodation;
    private Student assignedStudent;
    private final List<StateChangeListener> listeners = new ArrayList<>();
    
    /**
     * Listener interface for state changes
     */
    public interface StateChangeListener {
        void onStateChanged(StateContext context, RoomState oldState, RoomState newState);
    }
    
    public StateContext(Accommodation accommodation) {
        this.accommodation = accommodation;
        this.currentState = new VacantState(); // Initial state is vacant
    }
    
    /**
     * Change the current state
     * @param newState The new state to transition to
     */
    public void changeState(RoomState newState) {
        RoomState oldState = this.currentState;
        this.currentState = newState;
        
        // Notify listeners
        for (StateChangeListener listener : listeners) {
            listener.onStateChanged(this, oldState, newState);
        }
    }
    
    /**
     * Add state change listener
     * @param listener The listener to add
     */
    public void addStateChangeListener(StateChangeListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Remove state change listener
     * @param listener The listener to remove
     */
    public void removeStateChangeListener(StateChangeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Assign room to a student
     * @param student The student to assign
     * @return true if operation successful, false otherwise
     */
    public boolean assign(Student student) {
        return currentState.assign(this, student);
    }
    
    /**
     * Vacate the room
     * @return true if operation successful, false otherwise
     */
    public boolean vacate() {
        return currentState.vacate(this);
    }
    
    /**
     * Request maintenance for the room
     * @return true if operation successful, false otherwise
     */
    public boolean requestMaintenance() {
        return currentState.requestMaintenance(this);
    }
    
    /**
     * Mark room as available after maintenance
     * @return true if operation successful, false otherwise
     */
    public boolean completeMaintenance() {
        return currentState.completeMaintenance(this);
    }
    
    /**
     * Get the accommodation associated with this context
     * @return The accommodation
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    /**
     * Get the current state
     * @return The current state object
     */
    public RoomState getCurrentState() {
        return currentState;
    }
    
    /**
     * Get the state name
     * @return Name of the current state
     */
    public String getStateName() {
        return currentState.getStateName();
    }
    
    /**
     * Get assigned student
     * @return The assigned student or null if not assigned
     */
    public Student getAssignedStudent() {
        return assignedStudent;
    }
    
    /**
     * Set assigned student
     * @param student The student to assign
     */
    public void setAssignedStudent(Student student) {
        this.assignedStudent = student;
    }
} 