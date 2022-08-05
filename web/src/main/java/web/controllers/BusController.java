package web.controllers;

import common.domain.Bus;
import common.services.BusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.BusConverter;
import web.dto.bus.BusDTO;
import web.dto.bus.BusSaveDTO;
import web.dto.bus.BusesDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buses")
public class BusController {
    private static final Logger logger = LoggerFactory.getLogger(BusController.class);
    private final BusService busService;
    private final BusConverter busConverter;

    @GetMapping("/findAll")
    BusesDTO findAll() {
        List<Bus> buses = busService.findAll();
        return new BusesDTO(busConverter.convertModelsToDTOs(buses));
    }

    @GetMapping("/getAll")
    ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size) {
        logger.trace("findAll buses - method entered");
        Pageable paging = PageRequest.of(page, size);
        List<Bus> buses = busService.findAll(paging);
        logger.trace("findAll buses: " + buses);
        Map<String, Object> map = new HashMap<>();
        map.put("buses", buses);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getByModel")
    BusesDTO findByModelName(@RequestParam String name,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size) {
        logger.trace("get by model - method entered - name:" + name);

        List<Bus> buses = busService.findByModelName(name, PageRequest.of(page, size));
        BusesDTO busesDTO = new BusesDTO(busConverter.convertModelsToDTOs(buses));
        logger.trace("get by model result:" + buses);

        return busesDTO;
    }

    @PostMapping(value="/save")
    void save(@RequestBody BusSaveDTO busDTO){
        logger.trace("add bus - method entered - busDTO: " + busDTO);

        busService.save(busDTO.getModelName(), busDTO.getDriverId());

        logger.trace("add bus - bus added");
    }

    @PutMapping(value="/update/{id}")
    void update(@PathVariable Long id, @RequestBody BusDTO busDTO){
        logger.trace("update bus - method entered - busDTO: " + busDTO);

        busService.update(id, busDTO.getModelName());

        logger.trace("update bus - bus updated");
    }

    @DeleteMapping(value="/delete")
    ResponseEntity<?> delete(@PathVariable Long id){
        busService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/getDrivers")
    ResponseEntity<?> getAvailableDrivers() {
        return ResponseEntity.ok(busService.getAvailableDrivers());
    }
}
