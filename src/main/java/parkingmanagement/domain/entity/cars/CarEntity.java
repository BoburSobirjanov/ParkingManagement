package parkingmanagement.domain.entity.cars;

import jakarta.persistence.*;
import lombok.*;
import parkingmanagement.domain.entity.BaseEntity;
import parkingmanagement.domain.entity.orders.OrderEntity;

import java.util.List;

@Entity(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarEntity extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String number;
    @Enumerated(EnumType.STRING)
    private CarType type;
    @OneToMany
    private List<OrderEntity> orders;
}
