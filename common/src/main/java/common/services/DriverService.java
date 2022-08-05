package common.services;

import common.domain.Driver;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {
    List<Driver> findAll();

    List<Driver> findAll(Pageable paging);

    void save(String name, String cnp);

    void update(Long id, String name, String cnp);

    void deleteById(Long id);
}
