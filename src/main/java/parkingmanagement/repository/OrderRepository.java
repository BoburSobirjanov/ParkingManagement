package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.entity.orders.OrderEntity;
import parkingmanagement.domain.entity.orders.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findOrderEntitiesByCarNumber(String number);
    OrderEntity findOrderEntityByCarNumberAndStatus(String number,OrderStatus status);
    List<OrderEntity> findOrderEntitiesByStatus(OrderStatus status);
}
