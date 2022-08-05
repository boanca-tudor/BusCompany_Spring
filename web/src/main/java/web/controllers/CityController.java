package web.controllers;

import common.domain.City;
import common.services.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.CityConverter;
import web.dto.city.CitiesDTO;
import web.dto.city.CityDTO;

import java.util.List;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;
    private final CityConverter cityConverter;

    @GetMapping("/findAll")
    CitiesDTO findAll() {
        List<City> cities = cityService.findAll();
        return new CitiesDTO(cityConverter.convertModelsToDTOs(cities));
    }

    @GetMapping("/getAll")
    CitiesDTO findAll(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "3") int size){
        logger.trace("findAll cities - method entered");
        List<City> cities = cityService.findAll(PageRequest.of(page, size));
        var citiesDTO = new CitiesDTO(cityConverter.convertModelsToDTOs(cities));
        logger.trace("findAll cities: " + cities);
        return citiesDTO;
    }

    @PostMapping(value="/save")
    void save(@RequestBody CityDTO cityDTO){
        logger.trace("add city - method entered - cityDTO: " + cityDTO);

        var city = cityConverter.convertDtoToModel(cityDTO);
        cityService.save(city.getName(), city.getPopulation());

        logger.trace("add city - city added");
    }

    @PutMapping(value="/update/{id}")
    void update(@PathVariable Long id, @RequestBody CityDTO cityDTO){
        logger.trace("update city - method entered - cityDTO: " + cityDTO);

        var city = cityConverter.convertDtoToModel(cityDTO);
        cityService.update(id, city.getName(), city.getPopulation());

        logger.trace("update city - city added");
    }

    @DeleteMapping(value="/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        cityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
