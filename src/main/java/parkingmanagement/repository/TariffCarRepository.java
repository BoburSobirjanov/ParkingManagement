package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.tariff.Tariff_Car;

import java.util.UUID;

@Repository
public interface TariffCarRepository extends JpaRepository<Tariff_Car, UUID> {
    @Query(value = "select u from tariff_car as u where u.is_deleted=false and u.car_number=?1")
    Tariff_Car findTariff_CarByCar_number(String number);
}
