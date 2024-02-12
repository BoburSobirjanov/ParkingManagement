package parkingmanagement.domain.entity.orders;

import jakarta.persistence.*;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.place.PlaceEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntity extends BaseEntity {
    @Column(nullable = false)
    private String car_number;
    @Enumerated(EnumType.STRING)
    private CarType type;
    @ManyToOne
    private PlaceEntity place_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private UUID created_by;
    private UUID deleted_by;
    private LocalDateTime deleted_time;
    @Column(columnDefinition = "boolean default false")
    private boolean is_deleted;
}
