package client.controllers;

import common.domain.BusStop;
import common.domain.exceptions.BusManagementException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import web.converter.StopConverter;
import web.dto.stop.BusStopDTO;
import web.dto.stop.BusStopSaveDTO;
import web.dto.stop.BusStopUpdateDTO;
import web.dto.stop.BusStopsDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StopController {
    public static final Logger logger = LoggerFactory.getLogger(StopConverter.class);

    private final String url = "http://localhost:8085/stops/";
    private final ExecutorService executorService;
    private final RestTemplate restTemplate;
    private final StopConverter stopConverter;

    public CompletableFuture<Iterable<BusStop>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStopsDTO stops = restTemplate.getForObject(url + "getAll", BusStopsDTO.class);
                if (stops == null) {
                    throw new BusManagementException("Could not retrieve stops from server");
                }
                return stops.getBusStops().stream().map(stopConverter::convertDtoToModel).collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(Long busId, Long stationId, String stopTime){
        logger.trace("add stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStopSaveDTO s = new BusStopSaveDTO(busId, stationId, stopTime);
                restTemplate.postForObject(url + "save", s, BusStopDTO.class);
                return "Stop added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long id, String stopTime){
        logger.trace("update stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try{
                BusStopUpdateDTO s = new BusStopUpdateDTO(stopTime);
                s.setId(id);
                restTemplate.put(url + "update/{id}", s, s.getId());
                return "Stop updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "delete/{id}", id);
                return "Stop deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }
}
