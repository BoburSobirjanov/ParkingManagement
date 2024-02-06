package parkingmanagement.domain.dto.user;

import lombok.*;
import parkingmanagement.domain.entity.user.UserRole;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserForUser {
    private UUID id;
    private String name;
    private String number;
    private String email;
    private UserRole role;
}
