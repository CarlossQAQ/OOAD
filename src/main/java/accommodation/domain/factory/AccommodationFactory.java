package accommodation.domain.factory;

import accommodation.domain.model.Accommodation;

/** Factory Method 顶层接口 */
@FunctionalInterface
public interface AccommodationFactory {
    Accommodation create(int roomNumber);
}