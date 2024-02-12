package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.dto.tariff.TariffForUser;
import parkingmanagement.domain.entity.tariff.TariffEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, UUID> {
    @Query(value = "select u from tariff as u where u.is_deleted=false  and u.name=?1")
    Optional<TariffEntity> findTariffEntityByName(String name);
    @Query(value = "select u from tariff as u where u.is_deleted=false  and u.duration=?1")
    Optional<TariffEntity> findTariffEntityByDuration(Double duration);
    @Query(value = "select u from tariff as u where u.is_deleted=false  and u.id=?1")
    TariffEntity getTariffEntityById(UUID id);
    @Query(value = "select u from tariff as u where u.is_deleted=false")
    List<TariffForUser> getAll();
}
