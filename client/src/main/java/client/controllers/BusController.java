package client.controllers;

import common.domain.Bus;
import common.domain.exceptions.BusManagementException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import web.converter.BusConverter;
import web.dto.bus.BusDTO;
import web.dto.bus.BusesDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BusController {
    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    private final String url = "http://localhost:8085/buses/";
    private final ExecutorService executorService;
    private final RestTemplate restTemplate;
    private final BusConverter busConverter;

    public CompletableFuture<Iterable<Bus>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusesDTO buses = restTemplate.getForObject(url + "getAll", BusesDTO.class);
                if (buses == null)
                    throw new BusManagementException("Could not retrieve buses from server");
                return buses.getBuses().stream()
                        .map(busConverter::convertDtoToModel)
                        .collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String modelName){
        logger.trace("add bus - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusDTO b = new BusDTO(modelName);
                restTemplate.postForObject(url + "save", b, BusDTO.class);
                return "Bus added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long busId, String modelName){
        logger.trace("update bus - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try{
                BusDTO b = new BusDTO(modelName);
                b.setId(busId);
                restTemplate.put(url + "update/{id}", b, b.getId());
                return "Bus updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete bus - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "delete/{id}", id);
                return "Bus deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }
}
