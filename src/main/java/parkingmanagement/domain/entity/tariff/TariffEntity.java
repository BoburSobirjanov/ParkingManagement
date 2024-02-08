package parkingmanagement.domain.entity.tariff;

import jakarta.persistence.Entity;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;


@Entity(name = "tariff")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TariffEntity extends BaseEntity {
    private String name;
    private Double price;
    private Double duration;
    private TariffStatus status;
}
