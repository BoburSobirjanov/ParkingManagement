package parkingmanagement.response;

import lombok.*;
import parkingmanagement.domain.dto.user.UserForUser;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private UserForUser user;
}
