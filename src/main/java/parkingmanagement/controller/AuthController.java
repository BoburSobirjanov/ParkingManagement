package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkingmanagement.domain.dto.user.LoginDto;
import parkingmanagement.domain.dto.user.UserCreateDto;
import parkingmanagement.exception.RequestValidationException;
import parkingmanagement.response.JwtResponse;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public StandardResponse<JwtResponse> signUp(
            @RequestBody UserCreateDto userCreateDto,
            BindingResult bindingResult
            ) throws RequestValidationException{
        if (bindingResult.hasErrors()){
        List<ObjectError> allErrors = bindingResult.getAllErrors();
         throw new RequestValidationException(allErrors);
        }
        return userService.signUp(userCreateDto);
    }
    @PostMapping("/sign-in")
    public StandardResponse<JwtResponse> signIn(
            @RequestBody LoginDto loginDto
            ){
        return userService.signIn(loginDto);
    }
}
