package parkingmanagement.domain.dto.order;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderCreateDto {
    private UUID carId;
    private String startTime;
    private String endTime;
    private Double amount;
    private UUID placeId;
}
