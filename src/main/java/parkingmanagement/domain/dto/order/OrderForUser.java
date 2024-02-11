package parkingmanagement.domain.dto.order;

import lombok.*;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.orders.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderForUser {
    private UUID id;
    private String car_number;
    private CarType type;
    private UUID place_id;
    private LocalDateTime start_time;
    private OrderStatus status;
}
