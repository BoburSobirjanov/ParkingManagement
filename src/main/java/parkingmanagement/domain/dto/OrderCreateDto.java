package parkingmanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderCreateDto {
    private UUID carId;
    private String startTime;
    private String endTime;
    private Double amount;
    private UUID placeId;
}
