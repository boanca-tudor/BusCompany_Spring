package common.services;

import common.domain.BusStation;
import common.domain.BusStop;
import common.dto.repo.StationFilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusStationService {
    List<BusStation> findAll();

    List<BusStation> findAll(Pageable paging);

    List<StationFilterDTO> findByCityName(String name);

    void save(Long cityId, String name);

    void update(Long busStationId, String name);

    void deleteById(Long id);

    void addStop(Long busId, Long stationId, String stopTime);

    void removeStop(Long busId, Long stationId);

    void updateStop(Long busId, Long stationId, String newStopTime);

    List<BusStop> getAllStops();

    List<BusStop> getAllStopsForStation(Long stationId);
}
