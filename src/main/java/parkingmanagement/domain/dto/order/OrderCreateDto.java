package parkingmanagement.domain.dto.order;

import lombok.*;
import parkingmanagement.domain.entity.cars.CarType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderCreateDto {
    private String car_number;
    private String type;
    private String place_id;
}
