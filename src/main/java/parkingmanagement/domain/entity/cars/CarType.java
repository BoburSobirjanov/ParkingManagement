package parkingmanagement.domain.entity.cars;

import lombok.*;

@Getter
@AllArgsConstructor
public enum CarType {
    TRUCK(5.0),
    SEDAN(3.0),
    BIKE(1.0);
    private final double amount;
}
