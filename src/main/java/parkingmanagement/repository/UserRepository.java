package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = "select u from users as u where u.is_deleted=false and u.email=?1")
    UserEntity findUserEntityByEmail(String email);

    Optional<UserEntity> findUserEntityByNumber(String number);
    @Query(value = "select u from users as u where u.is_deleted=false  and u.id=?1")
    UserEntity getById(UUID id);
}
