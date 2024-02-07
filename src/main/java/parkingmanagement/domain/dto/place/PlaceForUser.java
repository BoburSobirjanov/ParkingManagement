package parkingmanagement.domain.dto.place;

import lombok.*;
import parkingmanagement.domain.entity.place.PlaceStatus;
import parkingmanagement.domain.entity.place.PlaceType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlaceForUser {
    private UUID id;
    private String floor;
    private String place;
    private PlaceType type;
    private PlaceStatus status;
}
