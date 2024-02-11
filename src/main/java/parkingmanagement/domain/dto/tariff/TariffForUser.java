package parkingmanagement.domain.dto.tariff;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TariffForUser {
    private UUID id;
    private String name;
    private Double price;
    private Double duration;
}
