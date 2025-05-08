package accommodation.domain.state;

import accommodation.domain.model.Student;

/**
 * State Pattern: Vacant state implementation
 * Represents a room that is currently not occupied
 */
public class VacantState implements RoomState {

    @Override
    public boolean assign(StateContext context, Student student) {
        if (student == null) {
            return false;
        }
        
        // Assign student and change state to occupied
        context.setAssignedStudent(student);
        context.changeState(new OccupiedState());
        return true;
    }

    @Override
    public boolean vacate(StateContext context) {
        // Room is already vacant, no action needed
        return true;
    }

    @Override
    public boolean requestMaintenance(StateContext context) {
        // Change state to maintenance
        context.changeState(new MaintenanceState());
        return true;
    }

    @Override
    public boolean completeMaintenance(StateContext context) {
        // Already vacant, no action needed
        return true;
    }

    @Override
    public String getStateName() {
        return "VACANT";
    }
} 