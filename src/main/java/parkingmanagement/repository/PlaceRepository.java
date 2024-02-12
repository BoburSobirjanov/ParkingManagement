package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.dto.place.PlaceForUser;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
    @Query(value = "select u from places as u where u.is_deleted=false  and u.type=?1")
    List<PlaceEntity> findPlaceEntityByType(PlaceType type);
    @Query(value = "select u from places as u where u.is_deleted=false  and u.place=?1")
    Optional<PlaceEntity> findPlaceEntityByPlace(String place);
    @Query(value = "select u from places as u where u.is_deleted=false  and u.floor=?1")
    Optional<PlaceEntity> findPlaceEntityByFloor(String floor);
    @Query(value = "select u from places as u where u.is_deleted=false  and u.id=?1")
    PlaceEntity findPlaceEntityById(UUID id);
    @Query(value = "select u from places as u where u.is_deleted=false")
    List<PlaceForUser> getAll();
}
