package parkingmanagement.domain.dto.place;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PlaceCreateDto {
    private String floor;
    private String place;
    private String type;
}
