package web.controllers;

import common.domain.BusStation;
import common.dto.repo.StationFilterDTO;
import common.dto.repo.StationsFilterDTO;
import org.springframework.data.domain.PageRequest;
import web.dto.station.*;
import common.services.BusStationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.StationConverter;
import web.dto.stop.BusStopSaveDTO;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("stations")
@RequiredArgsConstructor
public class StationController {
    private static final Logger logger = LoggerFactory.getLogger(StationController.class);
    private final BusStationService busStationService;
    private final StationConverter stationConverter;

    @GetMapping("/getAll")
    StationsDTO findAll(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size){
        logger.trace("findAll stations - method entered");
        List<BusStation> stations = busStationService.findAll(PageRequest.of(page, size));
        var stationsDTO = new StationsDTO(stationConverter.convertModelsToDTOs(stations));
        logger.trace("findAll stations: " + stations);
        return stationsDTO;
    }

    @GetMapping("/filterByCity/{name}")
    StationsFilterDTO findByCityName(@PathVariable String name) {
        logger.trace("get by city name - method entered - name:" + name);

        List<StationFilterDTO> stations = busStationService.findByCityName(name);
        logger.trace("get by city name result:" + stations);

        return new StationsFilterDTO(stations);
    }

    @PostMapping(value="/save")
    void save(@RequestBody StationSaveDTO stationSaveDTO){
        logger.trace("add station - method entered - stationDTO: " + stationSaveDTO);

        busStationService.save(stationSaveDTO.getCityId(), stationSaveDTO.getName());

        logger.trace("add station - station added");
    }

    @PutMapping(value="/update/{id}")
    void update(@PathVariable Long id, @RequestBody StationUpdateDTO stationUpdateDTO){
        logger.trace("update station - method entered - stationDTO: " + stationUpdateDTO);

        busStationService.update(id, stationUpdateDTO.getName());

        logger.trace("update station - station added");
    }

    @DeleteMapping(value="/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        busStationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAllStops")
    ResponseEntity<?> getAllStops() {
        var stops = busStationService.getAllStops();
        var map = new HashMap<String, Object>();
        map.put("stops", stops);
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/getStops")
    ResponseEntity<?> getStops(@RequestParam long stationId) {
        var stops = busStationService.getAllStopsForStation(stationId);
        var map = new HashMap<String, Object>();
        map.put("stops", stops);
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/addStop")
    void addStop(@RequestBody BusStopSaveDTO saveDTO) {
        busStationService.addStop(saveDTO.getBusId(), saveDTO.getStationId(), saveDTO.getStopTime());
    }

    @DeleteMapping("/deleteStop")
    void deleteStop(@RequestParam long busId,
                    @RequestParam long stationId) {
        busStationService.removeStop(busId, stationId);
    }
}
