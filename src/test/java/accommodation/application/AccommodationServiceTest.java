package accommodation.application;


import org.junit.jupiter.api.*;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.infrastructure.repository.InMemoryAccommodationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🛎️  Application Service 测试")
class AccommodationServiceTest {

    private AccommodationService service;
    private AccommodationRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryAccommodationRepository();
        service = new AccommodationService(new StandardAccommodationFactory(), repo);
        System.out.println("\n========= AccommodationServiceTest 准备完毕 =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= 结束 =========\n");
    }

    @Test
    @DisplayName("createAccommodation() 应持久化并注册房间")
    void createAccommodationPersistsAndRegisters() {
        Accommodation created = service.createAccommodation(401);

        System.out.println("创建并注册房间: " + created);
        System.out.println("Repository 当前列表: " + service.listAll());

        assertAll("房间持久化 + 查询验证",
                () -> assertTrue(repo.findByNumber(401).isPresent(), "Repository 应含房号 401"),
                () -> assertEquals(List.of(created), service.listAll().stream().toList(), "listAll() 应返回该房间")
        );
    }
}
