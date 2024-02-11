package parkingmanagement.domain.entity.orders;

import jakarta.persistence.*;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.place.PlaceEntity;

import java.time.LocalDateTime;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntity extends BaseEntity {
    @Column(nullable = false)
    private String carNumber;
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
}
