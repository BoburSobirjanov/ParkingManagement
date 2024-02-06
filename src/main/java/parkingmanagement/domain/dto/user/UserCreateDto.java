package parkingmanagement.domain.dto.user;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserCreateDto {
    private String name;
    private String number;
    private String email;
    private String password;
}
