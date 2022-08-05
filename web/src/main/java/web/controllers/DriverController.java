package web.controllers;

import common.domain.Driver;
import common.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.driver.DriverDTO;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("drivers")
public class DriverController {
    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
    private final DriverService driverService;

    @GetMapping("/getAll")
    ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size) {
        logger.trace("findAll drivers - method entered");
        Pageable paging = PageRequest.of(page, size);
        List<Driver> drivers = driverService.findAll(paging);
//        BusesDTO busesDTO = new BusesDTO(busConverter.convertModelsToDTOs(buses));
        logger.trace("findAll drivers: " + drivers);
        var map = new HashMap<String, Object>();
        map.put("drivers", drivers);
        return ResponseEntity.ok(map);
    }

    @PostMapping(value="/save")
    void save(@RequestBody DriverDTO driverDTO){
        logger.trace("add driver - method entered - driverDTO: " + driverDTO);

        driverService.save(driverDTO.getName(), driverDTO.getCnp());

        logger.trace("add driver - driver added");
    }

    @PutMapping(value="/update/{id}")
    void update(@PathVariable Long id, @RequestBody DriverDTO driverDTO){
        logger.trace("update driver - method entered - driverDTO: " + driverDTO);

        driverService.update(id, driverDTO.getName(), driverDTO.getCnp());

        logger.trace("update driver - driver updated");
    }

    @DeleteMapping(value="/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        driverService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
