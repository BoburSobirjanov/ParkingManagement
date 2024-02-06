package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.user.LoginDto;
import parkingmanagement.domain.dto.user.UserCreateDto;
import parkingmanagement.domain.dto.user.UserForUser;
import parkingmanagement.domain.entity.cars.CarEntity;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.domain.entity.user.UserRole;
import parkingmanagement.exception.AuthenticationFailedException;
import parkingmanagement.exception.DataHasAlreadyExistException;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.UserRepository;
import parkingmanagement.response.JwtResponse;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;
import parkingmanagement.service.auth.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public StandardResponse<JwtResponse> signUp(UserCreateDto userCreateDto){
        checkUserEmailAndPhoneNumber(userCreateDto.getEmail(), userCreateDto.getNumber());
        UserEntity user = modelMapper.map(userCreateDto, UserEntity.class);
        user.setRole(UserRole.EMPLOYER);
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setNumber(userCreateDto.getNumber());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user = userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        UserForUser userForUser = modelMapper.map(user, UserForUser.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userForUser)
                .build();
    return  StandardResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .message("Successfully signed up")
                .data(jwtResponse).build();
    }
    public void checkUserEmailAndPhoneNumber(String email, String phoneNumber) {
        if (userRepository.findUserEntityByEmail(email).isPresent()) {
            throw new UserBadRequestException("email already exists");
        }
        if (userRepository.findUserEntityByNumber(phoneNumber).isPresent()) {
            throw new UserBadRequestException("phone number already exists");
        }
    }

public StandardResponse<JwtResponse> signIn(LoginDto loginDto){
   UserEntity user = userRepository.findUserEntityByEmail(loginDto.getEmail())
           .orElseThrow(()-> new DataNotFoundException("User not found!"));
    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        UserForUser userForUser = modelMapper.map(user, UserForUser.class);
        JwtResponse jwtResponse = JwtResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(userForUser)
            .build();
    return StandardResponse.<JwtResponse>builder()
            .status(Status.SUCCESS)
            .message("Login successfully!")
            .data(jwtResponse)
            .build();
      } else {
             throw new AuthenticationFailedException("Something error during login!");
    }
}
}
