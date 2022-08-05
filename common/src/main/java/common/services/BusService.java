package common.services;

import common.domain.Bus;
import common.domain.Driver;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusService {
    List<Bus> findAll();

    List<Bus> findAll(Pageable paging);

    List<Bus> findByModelName(String name, Pageable paging);

    void save(String modelName, Long driverId);

    void update(Long id, String modelName);

    void deleteById(Long id);

    List<Driver> getAvailableDrivers();
}
