package parkingmanagement.domain.entity.orders;

import jakarta.persistence.*;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.place.PlaceEntity;

import java.time.LocalDateTime;
import java.util.List;

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
    private PlaceEntity placeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
