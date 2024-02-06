package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.place.PlaceEntity;

import java.util.UUID;
@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
}
