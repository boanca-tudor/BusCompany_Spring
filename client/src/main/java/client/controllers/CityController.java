package client.controllers;

import common.domain.City;
import common.domain.exceptions.BusManagementException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import web.converter.CityConverter;
import web.dto.city.CitiesDTO;
import web.dto.city.CityDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final String url = "http://localhost:8085/cities/";
    private final ExecutorService executorService;
    private final RestTemplate restTemplate;
    private final CityConverter cityConverter;

    public CompletableFuture<Iterable<City>> findAll(){
        logger.trace("findAll city - entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                CitiesDTO cities = restTemplate.getForObject(url + "getAll", CitiesDTO.class);
                if (cities == null)
                    throw new BusManagementException("Could not retrieve buses from server");
                return cities.getCities().stream()
                        .map(cityConverter::convertDtoToModel)
                        .collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String name, Integer population){
        logger.trace("add city - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                CityDTO dto = new CityDTO(name, population);
                restTemplate.postForObject(url + "save", dto, CityDTO.class);
                return "City added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long cityId, String name, Integer population){
        logger.trace("update city - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try{
                CityDTO dto = new CityDTO(name, population);
                dto.setId(cityId);
                restTemplate.put(url + "update/{id}", dto, dto.getId());
                return "City updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete city - method entered");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "delete/{id}", id);
                return "City deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }
}
