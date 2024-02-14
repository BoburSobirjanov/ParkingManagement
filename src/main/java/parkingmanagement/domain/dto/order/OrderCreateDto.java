package parkingmanagement.domain.dto.order;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderCreateDto {
    private String car_number;
    private String type;
    private String place_id;
}
