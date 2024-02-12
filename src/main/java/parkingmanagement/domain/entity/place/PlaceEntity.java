package parkingmanagement.domain.entity.place;

import jakarta.persistence.*;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.orders.OrderEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    private UUID created_by;
    private LocalDateTime deleted_time;
    private UUID  deleted_by;
    @Column(columnDefinition = "boolean default false")
    private boolean is_deleted;
}
