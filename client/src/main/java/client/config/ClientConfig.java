package client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import web.converter.BusConverter;
import web.converter.CityConverter;
import web.converter.StationConverter;
import web.converter.StopConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan({"client.ui", "client.controllers"})
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    @Bean
    BusConverter busConverter() {
        return new BusConverter();
    }

    @Bean
    CityConverter cityConverter() { return new CityConverter(); }

    @Bean
    StationConverter stationConverter() {
        return new StationConverter();
    }

    @Bean
    StopConverter stopConverter() {
        return new StopConverter();
    }
}
