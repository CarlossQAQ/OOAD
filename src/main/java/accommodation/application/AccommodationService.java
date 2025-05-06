package accommodation.application;

import accommodation.domain.factory.AccommodationFactory;
import accommodation.domain.model.Accommodation;
import accommodation.domain.repository.AccommodationRepository;
import accommodation.domain.singleton.HallRegistry;

import java.util.Collection;

/**
 * 用例级服务：协调 Factory + Repository
 */
public final class AccommodationService {
    private final AccommodationFactory factory;
    private final AccommodationRepository repository;

    public AccommodationService(AccommodationFactory factory,
                                AccommodationRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    /** 创建房间并注册到仓库与单例 Registry */
    public Accommodation createAccommodation(int roomNumber) {
        Accommodation acc = factory.create(roomNumber);
        repository.save(acc);
        HallRegistry.instance().register(acc);
        return acc;
    }

    public Collection<Accommodation> listAll() {
        return repository.findAll();
    }
}
