package web.converter;

import common.domain.Bus;
import org.springframework.stereotype.Component;
import web.dto.bus.BusDTO;

@Component
public class BusConverter extends BaseConverter<Long, Bus, BusDTO> {
    @Override
    public Bus convertDtoToModel(BusDTO dto) {
        var model = new Bus();
        model.setId(dto.getId());
        model.setModelName(dto.getModelName());
        return model;
    }

    @Override
    public BusDTO convertModelToDto(Bus bus) {
        var busDto = new BusDTO(bus.getModelName());
        busDto.setId(bus.getId());
        return busDto;
    }
}
