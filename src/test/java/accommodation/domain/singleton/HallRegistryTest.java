package accommodation.domain.singleton;


import org.junit.jupiter.api.*;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.model.Accommodation;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔒  Singleton HallRegistry Test")
class HallRegistryTest {

    @BeforeEach
    void clearRegistry() {
        HallRegistry.reset();   // Only visible for testing
        System.out.println("\n========= Registry reset =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= End =========\n");
    }

    @Test
    @DisplayName("instance() should return the same object")
    void singletonReturnsSameInstance() {
        HallRegistry r1 = HallRegistry.instance();
        HallRegistry r2 = HallRegistry.instance();

        System.out.printf("Instance 1 hash=%d, Instance 2 hash=%d%n",
                System.identityHashCode(r1), System.identityHashCode(r2));

        assertSame(r1, r2, "Multiple calls should return the same instance");
    }

    @Test
    @DisplayName("After register() should be able to find() successfully")
    void registerAndRetrieveRoom() {
        Accommodation room = new StandardAccommodationFactory().create(301);
        HallRegistry.instance().register(room);

        System.out.println("Registered room: " + room);
        System.out.println("Current Registry content: " + HallRegistry.instance().all());

        assertEquals(room, HallRegistry.instance().find(301), "Should be able to find the registered room");
    }
}
