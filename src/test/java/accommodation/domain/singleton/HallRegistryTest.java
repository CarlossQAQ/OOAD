package accommodation.domain.singleton;


import org.junit.jupiter.api.*;

import accommodation.domain.factory.StandardAccommodationFactory;
import accommodation.domain.model.Accommodation;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔒  Singleton HallRegistry 测试")
class HallRegistryTest {

    @BeforeEach
    void clearRegistry() {
        HallRegistry.reset();   // 仅测试可见
        System.out.println("\n========= Registry 已重置 =========");
    }

    @AfterEach
    void after() {
        System.out.println("========= 结束 =========\n");
    }

    @Test
    @DisplayName("instance() 应返回同一对象")
    void singletonReturnsSameInstance() {
        HallRegistry r1 = HallRegistry.instance();
        HallRegistry r2 = HallRegistry.instance();

        System.out.printf("实例 1 hash=%d, 实例 2 hash=%d%n",
                System.identityHashCode(r1), System.identityHashCode(r2));

        assertSame(r1, r2, "多次调用应得到同一实例");
    }

    @Test
    @DisplayName("register() 后应能 find() 成功")
    void registerAndRetrieveRoom() {
        Accommodation room = new StandardAccommodationFactory().create(301);
        HallRegistry.instance().register(room);

        System.out.println("已注册房间: " + room);
        System.out.println("当前 Registry 内容: " + HallRegistry.instance().all());

        assertEquals(room, HallRegistry.instance().find(301), "应能检索到刚注册的房间");
    }
}
