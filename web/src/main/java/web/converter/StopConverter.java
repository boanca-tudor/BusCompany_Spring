package web.converter;

import common.domain.BusStop;
import org.springframework.stereotype.Component;
import web.dto.stop.BusStopDTO;

@Component
public class StopConverter {
    public BusStop convertDtoToModel(BusStopDTO dto) {
        var model = new BusStop();
        model.setBus(dto.getBus());
        model.setBusStation(dto.getBusStation());
        model.setStopTime(dto.getStopTime());
        return model;
    }

    public BusStopDTO convertModelToDto(BusStop busStop) {
        var busStopDto = new BusStopDTO();
        busStopDto.setBus(busStop.getBus());
        busStopDto.setBusStation(busStop.getBusStation());
        busStopDto.setStopTime(busStop.getStopTime());
        return busStopDto;
    }
}
