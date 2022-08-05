package client.controllers;

import common.domain.BusStation;
import common.domain.exceptions.BusManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import web.converter.StationConverter;
import web.dto.station.StationSaveDTO;
import web.dto.station.StationUpdateDTO;
import web.dto.station.StationsDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Controller
public class StationController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final String url = "http://localhost:8085/stations/";
    private final ExecutorService executorService;
    private final RestTemplate restTemplate;
    private final StationConverter stationConverter;

    public StationController(ExecutorService executorService, RestTemplate restTemplate, StationConverter stationConverter) {
        this.executorService = executorService;
        this.restTemplate = restTemplate;
        this.stationConverter = stationConverter;
    }

    public CompletableFuture<Iterable<BusStation>> findAll(){
        logger.trace("findAll station - entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                StationsDTO cities = restTemplate.getForObject(url + "getAll", StationsDTO.class);
                if (cities == null)
                    throw new BusManagementException("Could not retrieve stations from server");
                return cities.getStations().stream()
                        .map(stationConverter::convertDtoToModel)
                        .collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String name, Long cityId){
        logger.trace("add station - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                StationSaveDTO dto = new StationSaveDTO(cityId, name);
                restTemplate.postForObject(url + "save", dto, StationSaveDTO.class);
                return "Station added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long id, String name){
        logger.trace("update station - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try{
                StationUpdateDTO dto = new StationUpdateDTO(name);
                dto.setId(id);
                restTemplate.put(url + "update/{id}", dto, dto.getId());
                return "Station updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete station - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "delete/{id}", id);
                return "Station deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }
}
