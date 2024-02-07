package parkingmanagement.domain.entity.place;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.orders.OrderEntity;

import java.util.List;

@Entity(name = "places")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlaceEntity extends BaseEntity {
    private String floor;
    private String place;
    @Enumerated(EnumType.STRING)
    private PlaceType type;
    @OneToMany
    private List<OrderEntity> orders;
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;
}
