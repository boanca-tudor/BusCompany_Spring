package web.converter;

import common.domain.City;
import org.springframework.stereotype.Component;
import web.dto.city.CityDTO;

@Component
public class CityConverter extends BaseConverter<Long, City, CityDTO> {
    @Override
    public City convertDtoToModel(CityDTO dto) {
        var model = new City();
        model.setName(dto.getName());
        model.setId(dto.getId());
        model.setPopulation(dto.getPopulation());

        return model;
    }

    @Override
    public CityDTO convertModelToDto(City city) {
        var dto = new CityDTO(city.getName(), city.getPopulation());
        dto.setId(city.getId());

        return dto;
    }
}
