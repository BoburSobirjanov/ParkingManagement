package parkingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parkingmanagement.domain.dto.order.OrderForUser;
import parkingmanagement.domain.entity.orders.OrderEntity;
import parkingmanagement.domain.entity.orders.OrderStatus;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query(value = "select u from orders as u where u.is_deleted=false  and u.status='PROGRESS' and u.car_number=?1")
    OrderEntity findOrderEntityByCarNumberAndStatus(String number);
    @Query(value = "select u from orders as u where u.is_deleted=false  and u.status=?1")
    List<OrderEntity> findOrderEntitiesByStatus(OrderStatus status);
    @Query(value = "select u from orders as u where u.is_deleted=false  and u.id=?1")
    OrderEntity findOrderEntityById(UUID id);
    @Query(value = "select u from orders as u where u.is_deleted=false  and u.car_number=?1")
    List<OrderForUser> findOrderEntityByCarNumber(String carNumber);

    @Query(value = "select u from orders as u where u.is_deleted=false")
    List<OrderForUser> getAll();
}
