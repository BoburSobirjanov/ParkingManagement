package parkingmanagement.domain.entity.tariff;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tariff_car")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tariff_Car extends BaseEntity {
    private String car_number;
    private UUID tariff_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private UUID assigned_by;
    @Column(columnDefinition = "boolean default false")
    private boolean is_deleted;
}
