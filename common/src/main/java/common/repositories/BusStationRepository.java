package common.repositories;

import common.domain.BusStation;
import common.dto.repo.StationFilterDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusStationRepository extends Repository<BusStation, Long>{
    @Query(
            "select new common.dto.repo.StationFilterDTO(bs.id, bs.name) " +
                    "from BusStation bs inner join City c on bs.city.id = c.id " +
                    "where c.name like %:name%"
    )
    List<StationFilterDTO> filterByCityName(@Param("name") String cityName);
}
