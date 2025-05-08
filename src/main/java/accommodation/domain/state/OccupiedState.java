package accommodation.domain.state;

import accommodation.domain.model.Student;

/**
 * State Pattern: Occupied state implementation
 * Represents a room that is currently occupied by a student
 */
public class OccupiedState implements RoomState {

    @Override
    public boolean assign(StateContext context, Student student) {
        // Cannot assign an occupied room
        return false;
    }

    @Override
    public boolean vacate(StateContext context) {
        // Clear assigned student and change state to vacant
        context.setAssignedStudent(null);
        context.changeState(new VacantState());
        return true;
    }

    @Override
    public boolean requestMaintenance(StateContext context) {
        // Cannot request maintenance while occupied
        // Must first vacate the room
        return false;
    }

    @Override
    public boolean completeMaintenance(StateContext context) {
        // Not in maintenance state
        return false;
    }

    @Override
    public String getStateName() {
        return "OCCUPIED";
    }
} 