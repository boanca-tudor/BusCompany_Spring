package common.services;

import common.domain.Driver;
import common.domain.exceptions.BusManagementException;
import common.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public List<Driver> findAll(Pageable paging) {
        Page<Driver> driverPage = driverRepository.findAll(paging);

        return driverPage.getContent();
    }

    @Override
    public void save(String name, String cnp) {
        Driver driver = new Driver(name, cnp);
        driverRepository.save(driver);
    }

    @Override
    public void update(Long id, String name, String cnp) {
        driverRepository.findById(id)
                .ifPresentOrElse(
                        (d) -> {
                            d.setName(name);
                            d.setCnp(cnp);
                        },
                        () -> {
                            throw new BusManagementException("Driver does not exist!");
                        }
                );
    }

    @Override
    public void deleteById(Long id) {
        driverRepository.findById(id)
                .ifPresentOrElse(
                        d -> driverRepository.deleteById(id),
                        () -> {
                            throw new BusManagementException("Driver does not exist!");
                        }
                );
    }
}
