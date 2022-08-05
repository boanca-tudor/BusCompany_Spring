package common.services;

import common.domain.City;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.Future;

public interface CityService {
    List<City> findAll();

    List<City> findAll(Pageable paging);

    void save(String name, Integer population);

    void update(Long id, String name, Integer population);

    void deleteById(Long id);
}
