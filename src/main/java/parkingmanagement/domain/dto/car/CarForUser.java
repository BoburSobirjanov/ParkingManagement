package parkingmanagement.domain.dto.car;

import lombok.*;
import parkingmanagement.domain.entity.cars.CarType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CarForUser {
   private UUID id;
   private String number;
   private CarType type;
}
