package common.repositories;

import common.domain.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusRepository extends Repository<Bus, Long> {
    Page<Bus> findBusesByModelNameIsLike(String modelName, Pageable pageable);
}
