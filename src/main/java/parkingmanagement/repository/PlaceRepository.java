package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceType;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
    Optional<PlaceEntity> findPlaceEntityByType(PlaceType type);
    Optional<PlaceEntity> findPlaceEntityByPlace(String place);
    Optional<PlaceEntity> findPlaceEntityByFloor(String floor);
}
