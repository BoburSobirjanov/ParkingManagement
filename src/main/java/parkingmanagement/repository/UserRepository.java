package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findUserEntityByEmail(String email);
    Optional<UserEntity> findUserEntityByNumber(String number);
    UserEntity getById(UUID id);
}
