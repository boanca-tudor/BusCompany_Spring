package common.services;

import common.domain.City;
import common.domain.exceptions.BusManagementException;
import common.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    private final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public List<City> findAll() {
        logger.info("entered findAll");

        return cityRepository.findAll();
    }

    @Override
    public List<City> findAll(Pageable paging) {
        Page<City> cityPage = cityRepository.findAll(paging);

        return cityPage.getContent();
    }

    @Override
    public void save(String name, Integer population) {
        logger.info("entered save");

        City city = new City(name, population);

        cityRepository.save(city);

        logger.info("left save");
    }

    @Override
    @Transactional
    public void update(Long id, String name, Integer population) {
        logger.info("entered update");

        cityRepository.findById(id)
                .ifPresentOrElse(
                        (city) -> {
                            city.setName(name);
                            city.setPopulation(population);
                        },
                        () -> {
                            throw new BusManagementException("Bus does not exist!");
                        }
                );

        logger.trace("left update");
    }

    @Override
    public void deleteById(Long id) {
        logger.info("entered delete");

        cityRepository.findById(id)
                .ifPresentOrElse(
                        (city) -> cityRepository.deleteById(id),
                        () -> {
                            throw new BusManagementException("City does not exist!");
                        }
                );

        logger.info("left delete");
    }
}
