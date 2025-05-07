## Main Features

- Support for creating and managing two types of rooms: Standard and Superior
- Support for room registration, query, and persistence
- Provides in-memory repository implementation, convenient for testing and demonstration

## Design Patterns Used

This project mainly uses the following three design patterns:

1. **Factory Method Pattern**
   - Through `AccommodationFactory` and its implementations, flexibly create different types of room objects.

2. **Builder Pattern**
   - Through `AccommodationBuilder` and its implementations, construct room objects step by step, supporting fluent API.

3. **Singleton Pattern**
   - Through `HallRegistry` implement a globally unique room registry, ensuring data consistency.

## Key Classes Description

- `Accommodation`: Domain model, representing a dormitory room.
- `RoomType`: Room type enumeration (Standard/Superior).
- `AccommodationFactory`, `StandardAccommodationFactory`, `SuperiorAccommodationFactory`: Factory Method pattern related.
- `AccommodationBuilder`, `StandardRoomBuilder`, `SuperiorRoomBuilder`: Builder pattern related.
- `AccommodationRepository`, `InMemoryAccommodationRepository`: Repository interface and in-memory implementation.
- `HallRegistry`: Singleton pattern implementation of global registry.
- `AccommodationService`: Application service, coordinates factory, repository and registry.

## How to Run

1. **Compile the project**
   ```bash
   mvn clean compile
   ```

2. **Run tests**
   ```bash
   mvn test
   ```

3. **Main test classes**
   - `AccommodationServiceTest`
   - `AccommodationFactoryTest`
   - `HallRegistryTest`

## UML Class Diagram

The project adopts a layered architecture, core class diagram is as follows:

> ![UML Class Diagram](./doc/class-diagram.png)  

