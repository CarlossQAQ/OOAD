package accommodation.domain.singleton;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import accommodation.domain.model.Accommodation;

public final class HallRegistry {
    private static final HallRegistry INSTANCE = new HallRegistry();

    private final Map<Integer, Accommodation> rooms = new ConcurrentHashMap<>();

    private HallRegistry() {}

    public static HallRegistry instance() {
        return INSTANCE;
    }

    public void register(Accommodation accommodation) {
        rooms.put(accommodation.getNumber(), accommodation);
    }

    public Accommodation find(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public Map<Integer, Accommodation> all() {
        return Collections.unmodifiableMap(rooms);
    }
    
    /**
     * Reset registry for testing purposes.
     * This method should only be used in test code.
     */
    public static void resetForTest() {
        INSTANCE.rooms.clear();
    }
    
    static void reset() {
        INSTANCE.rooms.clear();
    }
}
