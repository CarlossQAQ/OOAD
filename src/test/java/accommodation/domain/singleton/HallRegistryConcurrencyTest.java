package accommodation.domain.singleton;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔄 HallRegistry Concurrency Test")
class HallRegistryConcurrencyTest {

    @BeforeEach
    void clearRegistry() {
        HallRegistry.resetForTest();
        System.out.println("\n========= Registry reset for concurrency test =========");
    }

    @Test
    @DisplayName("Registry should handle concurrent registrations safely")
    void registryShouldHandleConcurrentRegistrationsSafely() throws InterruptedException {
        // Number of threads and operations
        final int threadCount = 10;
        final int operationsPerThread = 100;
        
        // Create countdown latch for synchronization
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        
        // Shared registry
        HallRegistry registry = HallRegistry.instance();
        
        // Atomic counter for room numbers
        AtomicInteger roomNumberCounter = new AtomicInteger(1000);
        
        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        // Launch threads
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // Wait for the start signal
                    startLatch.await();
                    
                    // Perform operations
                    for (int j = 0; j < operationsPerThread; j++) {
                        // Get a unique room number
                        int roomNumber = roomNumberCounter.getAndIncrement();
                        
                        // Create and register a room
                        Accommodation room = new Accommodation(
                                roomNumber,
                                (roomNumber % 2 == 0) ? RoomType.STANDARD : RoomType.SUPERIOR,
                                BigDecimal.valueOf(700 + (roomNumber % 300))
                        );
                        
                        registry.register(room);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }
        
        // Start all threads simultaneously
        startLatch.countDown();
        
        // Wait for all threads to finish (timeout after 10 seconds)
        boolean allFinished = finishLatch.await(10, TimeUnit.SECONDS);
        
        // Shutdown executor
        executor.shutdown();
        
        // Verify all threads completed
        assertTrue(allFinished, "All threads should complete within timeout");
        
        // Verify registry state
        Map<Integer, Accommodation> allRooms = registry.all();
        int expectedRoomCount = threadCount * operationsPerThread;
        assertEquals(expectedRoomCount, allRooms.size(), 
                "Registry should contain all registered rooms without data loss");
        
        // Verify some random rooms exist
        List<Integer> roomNumbers = new ArrayList<>(allRooms.keySet());
        for (int i = 0; i < 10; i++) {
            int randomIndex = (int) (Math.random() * roomNumbers.size());
            int roomNumber = roomNumbers.get(randomIndex);
            assertNotNull(registry.find(roomNumber), 
                    "Should find randomly selected room " + roomNumber);
        }
        
        System.out.println("Concurrent test completed with " + allRooms.size() + " rooms registered");
    }
} 