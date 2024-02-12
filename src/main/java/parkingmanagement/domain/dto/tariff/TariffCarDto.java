package parkingmanagement.domain.dto.tariff;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TariffCarDto {
    private String car_number;
    private UUID tariff_id;
}
