package common.services;

import common.domain.Bus;
import common.domain.Driver;
import common.domain.exceptions.BusManagementException;
import common.repositories.BusRepository;
import common.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;

    private final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    @Override
    public List<Bus> findAll() {
        logger.trace("entered bus find all");

        return busRepository.findAll();
    }

    @Override
    public List<Bus> findByModelName(String name, Pageable paging) {
        logger.trace("entered bus find by model name");

        Page<Bus> busPage = busRepository.findBusesByModelNameIsLike('%' + name + '%', paging);

        return busPage.getContent();
    }

    @Override
    public void save(String modelName, Long driverId) {
        logger.trace("entered add bus");

        driverRepository.findById(driverId).ifPresentOrElse(
                d -> {
                    Bus b = new Bus(modelName, d);
                    busRepository.save(b);
                },
                () -> {
                    throw new BusManagementException("Driver not found!");
                }
        );

        logger.trace("left update bus");
    }

    @Override
    @Transactional
    public void update(Long id, String modelName) {
        logger.trace("entered update bus");

        busRepository.findById(id)
                .ifPresentOrElse(
                        (b) -> {
                            b.setModelName(modelName);
                        },
                        () -> {
                            throw new BusManagementException("Bus does not exist!");
                        }
                );

        logger.trace("left update bus");
    }

    @Override
    public void deleteById(Long id) {
        logger.trace("entered delete bus");

        busRepository.findById(id)
                .ifPresentOrElse(
                        (b) -> busRepository.deleteById(id),
                        () -> {
                            throw new BusManagementException("Bus does not exist!");
                        }
                );

        logger.trace("left delete bus");
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        var drivers = driverRepository.findAll();
        var buses = busRepository.findAll();

        buses.forEach(
                        b -> {
                            drivers.remove(b.getDriver());
                        }
                );

        return drivers;
    }

    public List<Bus> findAll(Pageable paging) {
        Page<Bus> busPage = busRepository.findAll(paging);

        return busPage.getContent();
    }
}
