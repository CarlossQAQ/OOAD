package accommodation.infrastructure.repository;

import accommodation.domain.model.Accommodation;
import accommodation.domain.model.RoomType;
import accommodation.domain.repository.AccommodationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🔄 InMemoryAccommodationRepository Concurrency Test")
class InMemoryRepositoryConcurrencyTest {

    private AccommodationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAccommodationRepository();
        System.out.println("\n========= Repository initialized for concurrency test =========");
    }

    @Test
    @DisplayName("Repository should handle concurrent saves safely")
    void repositoryShouldHandleConcurrentSavesSafely() throws InterruptedException {
        // Number of threads and operations
        final int threadCount = 10;
        final int operationsPerThread = 100;
        
        // Create countdown latch for synchronization
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        
        // Atomic counter for room numbers
        AtomicInteger roomNumberCounter = new AtomicInteger(2000);
        
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
                        
                        // Create and save a room
                        Accommodation room = new Accommodation(
                                roomNumber,
                                (roomNumber % 2 == 0) ? RoomType.STANDARD : RoomType.SUPERIOR,
                                BigDecimal.valueOf(700 + (roomNumber % 300))
                        );
                        
                        repository.save(room);
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
        
        // Verify repository state
        Collection<Accommodation> allRooms = repository.findAll();
        int expectedRoomCount = threadCount * operationsPerThread;
        assertEquals(expectedRoomCount, allRooms.size(), 
                "Repository should contain all saved rooms without data loss");
        
        // Verify we can find some random rooms
        for (int i = 0; i < 10; i++) {
            int roomNumber = 2000 + (int) (Math.random() * expectedRoomCount);
            Optional<Accommodation> room = repository.findByNumber(roomNumber);
            if (roomNumber < 2000 + expectedRoomCount) {
                assertTrue(room.isPresent(), "Should find room " + roomNumber);
            }
        }
        
        System.out.println("Concurrent test completed with " + allRooms.size() + " rooms saved");
    }
    
    @Test
    @DisplayName("Repository should handle concurrent operations of different types")
    void repositoryShouldHandleMixedConcurrentOperations() throws InterruptedException {
        // Pre-populate with some rooms
        for (int i = 3000; i < 3100; i++) {
            repository.save(new Accommodation(
                    i,
                    (i % 2 == 0) ? RoomType.STANDARD : RoomType.SUPERIOR,
                    BigDecimal.valueOf(700)
            ));
        }
        
        // Number of threads
        final int threadCount = 20;
        
        // Create countdown latch for synchronization
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        
        // Atomic counter for unique room numbers for new saves
        AtomicInteger roomNumberCounter = new AtomicInteger(3100);
        
        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        // Launch threads with mixed operations
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                try {
                    // Wait for the start signal
                    startLatch.await();
                    
                    // Different operations based on thread index modulo 3
                    switch (threadIndex % 4) {
                        case 0: // Save new rooms
                            for (int j = 0; j < 50; j++) {
                                int roomNumber = roomNumberCounter.getAndIncrement();
                                repository.save(new Accommodation(
                                        roomNumber,
                                        RoomType.STANDARD,
                                        BigDecimal.valueOf(700)
                                ));
                            }
                            break;
                            
                        case 1: // Find existing rooms
                            for (int j = 0; j < 100; j++) {
                                int roomNumber = 3000 + (int) (Math.random() * 100);
                                repository.findByNumber(roomNumber);
                            }
                            break;
                            
                        case 2: // Get all rooms
                            for (int j = 0; j < 20; j++) {
                                repository.findAll();
                                Thread.sleep(5); // Brief pause to simulate processing
                            }
                            break;
                            
                        case 3: // Update existing rooms
                            for (int j = 0; j < 50; j++) {
                                int roomNumber = 3000 + (int) (Math.random() * 100);
                                Optional<Accommodation> roomOpt = repository.findByNumber(roomNumber);
                                if (roomOpt.isPresent()) {
                                    // Create updated room with same number but different price
                                    Accommodation original = roomOpt.get();
                                    Accommodation updated = new Accommodation(
                                            original.getNumber(),
                                            original.getType(),
                                            BigDecimal.valueOf(800)
                                    );
                                    repository.save(updated);
                                }
                            }
                            break;
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
        
        // Verify final repository state
        Collection<Accommodation> allRooms = repository.findAll();
        System.out.println("Mixed concurrent operations completed with " + allRooms.size() + " total rooms");
        
        // Verify basic counts
        assertTrue(allRooms.size() >= 100, "Repository should contain at least the initial 100 rooms");
        
        // Check some room details
        for (int i = 3000; i < 3100; i++) {
            Optional<Accommodation> room = repository.findByNumber(i);
            assertTrue(room.isPresent(), "Original room " + i + " should still exist");
        }
    }
} 