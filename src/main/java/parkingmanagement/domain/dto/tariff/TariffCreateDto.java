package parkingmanagement.domain.dto.tariff;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TariffCreateDto {
    private String name;
    private Double price;
    private Double duration;
}
