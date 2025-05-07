package accommodation.application;


import accommodation.application.impl.AccommodationServiceImpl;
import org.junit.jupiter.api.*;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.infrastructure.repository.InMemoryAccommodationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🛎️  Application Service Test")
class AccommodationServiceTest {

    private AccommodationService service;
    private AccommodationRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryAccommodationRepository();
        service = new AccommodationServiceImpl(new StandardAccommodationFactory(), repo);
        System.out.println("\n========= AccommodationServiceTest ready =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= End =========\n");
    }

    @Test
    @DisplayName("createAccommodation() should persist and register room")
    void createAccommodationPersistsAndRegisters() {
        Accommodation created = service.createAccommodation(401);

        System.out.println("Created and registered room: " + created);
        System.out.println("Repository current list: " + service.listAll());

        assertAll("Room persistence + query validation",
                () -> assertTrue(repo.findByNumber(401).isPresent(), "Repository should contain room 401"),
                () -> assertEquals(List.of(created), service.listAll().stream().toList(), "listAll() should return the room")
        );
    }
}
