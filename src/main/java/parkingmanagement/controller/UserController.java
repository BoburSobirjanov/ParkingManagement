package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.user.UserForUser;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public StandardResponse<String> deleteUser(
            @RequestParam String email,
            Principal principal
    ){
        return userService.deleteUser(email,principal);
    }
    @PutMapping("/add-admin")
    @PreAuthorize(value = "hasRole('OWNER')")
    public StandardResponse<UserForUser> addAdmin(
            @RequestParam String email
    ){
        return userService.addAdmin(email);
    }

    @PutMapping("/remove-admin")
    @PreAuthorize(value = "hasRole('OWNER')")
    public StandardResponse<UserForUser> removeAdmin(
            @RequestParam String email
    ){
        return userService.removeAdmin(email);
    }
    @GetMapping("{id}/get-user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<UserEntity> getUserById(
            @PathVariable UUID id
            ){
        return userService.getUserById(id);
    }
}
