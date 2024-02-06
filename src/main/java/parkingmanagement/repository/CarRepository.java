package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.cars.CarEntity;

import java.util.UUID;
@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    CarEntity findCarEntityByNumber(String number);
}
