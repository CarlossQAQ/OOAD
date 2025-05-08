package accommodation.domain.state;

import accommodation.domain.model.Student;

/**
 * State Pattern: Maintenance state implementation
 * Represents a room that is currently under maintenance
 */
public class MaintenanceState implements RoomState {

    @Override
    public boolean assign(StateContext context, Student student) {
        // Cannot assign a room under maintenance
        return false;
    }

    @Override
    public boolean vacate(StateContext context) {
        // No student to vacate in a maintenance room
        return false;
    }

    @Override
    public boolean requestMaintenance(StateContext context) {
        // Already in maintenance
        return true;
    }

    @Override
    public boolean completeMaintenance(StateContext context) {
        // Maintenance complete, change to vacant state
        context.changeState(new VacantState());
        return true;
    }

    @Override
    public String getStateName() {
        return "MAINTENANCE";
    }
} 