package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.tariff.TariffEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, UUID> {

    Optional<TariffEntity> findTariffEntityByName(String name);
    Optional<TariffEntity> findTariffEntityByDuration(Double duration);
}
