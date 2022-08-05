package common.services;

import common.domain.BusStation;
import common.domain.BusStop;
import common.domain.City;
import common.domain.exceptions.BusManagementException;
import common.dto.repo.StationFilterDTO;
import common.repositories.BusRepository;
import common.repositories.BusStationRepository;
import common.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusStationServiceImpl implements BusStationService {
    private final BusStationRepository busStationRepository;
    private final BusRepository busRepository;
    private final CityRepository cityRepository;

    private final Logger logger = LoggerFactory.getLogger(BusStationServiceImpl.class);

    @Override
    public List<BusStation> findAll() {
        logger.trace("entered bus station find all");

        return busStationRepository.findAll();
    }

    @Override
    public List<BusStation> findAll(Pageable paging) {
        Page<BusStation> stationPage = busStationRepository.findAll(paging);

        return stationPage.getContent();
    }

    @Override
    public List<StationFilterDTO> findByCityName(String name) {
        logger.trace("entered bus station filter by city name");

        return busStationRepository.filterByCityName(name);
    }

    @Override
    public void save(Long cityId, String name) {
        logger.trace("entered add bus station");

        Optional<City> city = cityRepository.findById(cityId);

        city.ifPresentOrElse(
                (city1 -> {
                    BusStation busStation = new BusStation(name, city1);

                    busStationRepository.save(busStation);
                }),
                () -> {
                    throw new BusManagementException("City does not exist!");
                }
        );

        logger.trace("left add bus station");
    }

    @Override
    @Transactional
    public void update(Long busStationId, String name) {
        logger.trace("entered update bus station");

        busStationRepository.findById(busStationId)
                .ifPresentOrElse((station -> {
                    station.setName(name);
                }),
                () -> {
                    throw new BusManagementException("Station does not exist!");
                }
        );

        logger.trace("left update bus station");
    }

    @Override
    public void deleteById(Long id) {
        logger.trace("entered delete bus station");

        busStationRepository.findById(id)
                .ifPresentOrElse(
                        (b) -> busStationRepository.deleteById(id),
                        () -> {
                            throw new BusManagementException("Station does not exist!");
                        }
                );

        logger.trace("left delete bus station");
    }

    @Override
    @Transactional
    public void addStop(Long busId, Long stationId, String stopTime) {
        this.busRepository.findById(busId).ifPresentOrElse(
                b -> {
                    this.busStationRepository.findById(stationId).ifPresentOrElse(
                            s -> s.addStop(b, stopTime),
                            () -> {
                                throw new BusManagementException("Station not found!");
                            }
                    );
                },
                () -> {
                    throw new BusManagementException("Bus not found!");
                }
        );
    }

    @Override
    @Transactional
    public void removeStop(Long busId, Long stationId) {
        this.busRepository.findById(busId).ifPresentOrElse(
                b -> {
                    this.busStationRepository.findById(stationId).ifPresentOrElse(
                            s -> s.removeStop(b),
                            () -> {
                                throw new BusManagementException("Station not found!");
                            }
                    );
                },
                () -> {
                    throw new BusManagementException("Bus not found!");
                }
        );
    }

    @Override
    public void updateStop(Long busId, Long stationId, String newStopTime) {
        this.busRepository.findById(busId).ifPresentOrElse(
                b -> {
                    this.busStationRepository.findById(stationId).ifPresentOrElse(
                            s -> s.updateStop(b, newStopTime),
                            () -> {
                                throw new BusManagementException("Station not found!");
                            }
                    );
                },
                () -> {
                    throw new BusManagementException("Bus not found!");
                }
        );
    }

    @Override
    public List<BusStop> getAllStops() {
        List<BusStop> busStops = new ArrayList<>();
        List<BusStation> stations = busStationRepository.findAll();

        for (var station: stations) {
            busStops.addAll(station.getBusStops());
        }
        return busStops;
    }

    @Override
    public List<BusStop> getAllStopsForStation(Long stationId) {
        return new ArrayList<>(this.busStationRepository.findById(stationId).get().getBusStops());
    }
}
