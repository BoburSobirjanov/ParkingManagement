package parkingmanagement.domain.dto.car;

import lombok.*;
import parkingmanagement.domain.entity.cars.CarType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CarCreateDto {
    private String number;
    private CarType type;
}
