package web.converter;

import common.domain.BusStation;
import org.springframework.stereotype.Component;
import web.dto.station.StationDTO;

@Component
public class StationConverter extends BaseConverter<Long, BusStation, StationDTO> {
    @Override
    public BusStation convertDtoToModel(StationDTO dto) {
        var model = new BusStation();
        model.setName(dto.getName());
        model.setCity(dto.getCity());
        model.setId(dto.getId());

        return model;
    }

    @Override
    public StationDTO convertModelToDto(BusStation station) {
        var dto = new StationDTO();
        dto.setId(station.getId());
        dto.setCity(station.getCity());
        dto.setName(station.getName());

        return dto;
    }
}
