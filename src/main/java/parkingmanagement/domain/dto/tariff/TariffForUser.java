package parkingmanagement.domain.dto.tariff;

import lombok.*;
import parkingmanagement.domain.entity.tariff.TariffStatus;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class TariffForUser {
    private UUID id;
    private String name;
    private Double price;
    private Double duration;
    private TariffStatus status;
}
