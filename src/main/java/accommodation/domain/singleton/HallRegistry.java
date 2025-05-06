package accommodation.domain.singleton;

import accommodation.domain.model.Accommodation;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton：全局宿舍房间注册表<br/>
 * 保证唯一实例 & 线程安全
 */
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
    static void reset() {
        INSTANCE.rooms.clear();
    }
}
