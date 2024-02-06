package parkingmanagement.domain.entity.orders;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.cars.CarEntity;

import java.time.LocalDateTime;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntity extends BaseEntity {
    @ManyToOne
    private CarEntity carId;
    @ManyToOne
    private PlaceEntity placeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double amount;
}
