package parkingmanagement.domain.entity.cars;

import lombok.*;

@Getter
@AllArgsConstructor
public enum CarType {
    TRUCK(3.0),
    SEDAN(1.0);
    private final double amount;
}
