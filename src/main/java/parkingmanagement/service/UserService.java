package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.user.LoginDto;
import parkingmanagement.domain.dto.user.UserCreateDto;
import parkingmanagement.domain.dto.user.UserForUser;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.domain.entity.user.UserRole;
import parkingmanagement.exception.AuthenticationFailedException;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.UserRepository;
import parkingmanagement.response.JwtResponse;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;
import parkingmanagement.service.auth.JwtService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

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
        UserEntity user = userRepository.findUserEntityByEmail(email);
        if (user!=null) {
            throw new UserBadRequestException("email already exists");
        }
        if (userRepository.findUserEntityByNumber(phoneNumber).isPresent()) {
            throw new UserBadRequestException("phone number already exists");
        }
    }

public StandardResponse<JwtResponse> signIn(LoginDto loginDto){
   UserEntity user = userRepository.findUserEntityByEmail(loginDto.getEmail());
    if (user==null){
       log.error("User not found");
       throw new DataNotFoundException("Not found");
    }
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
  public StandardResponse<String> deleteUser(String email, Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(email);
      if (user==null){
          log.error("User not found!");
          throw new DataNotFoundException("Not found!");
      }
        user.set_deleted(true);
        UserEntity userEntity =userRepository.findUserEntityByEmail(principal.getName());
        user.setDeleted_by(userEntity.getId());
        user.setDeleted_time(LocalDateTime.now());
        userRepository.save(user);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("User deleted successfully!")
                .data("Deleted")
                .build();
  }

  public StandardResponse<UserForUser> removeAdmin(String email){
        UserEntity user = userRepository.findUserEntityByEmail(email);
        if (user==null){
            throw new DataNotFoundException("User not found!");
        }
        user.setRole(UserRole.EMPLOYER);
        userRepository.save(user);
        UserForUser userForUser = modelMapper.map(user,UserForUser.class);
        return StandardResponse.<UserForUser>builder()
                .status(Status.SUCCESS)
                .message("Role changed successfully!")
                .data(userForUser)
                .build();
  }

  public StandardResponse<UserForUser> addAdmin(String email){
        UserEntity user = userRepository.findUserEntityByEmail(email);
      if (user==null){
          log.error("User not found!");
          throw new DataNotFoundException("Not found!");
      }
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
        UserForUser userForUser = modelMapper.map(user, UserForUser.class);
        return StandardResponse.<UserForUser>builder()
                .status(Status.SUCCESS)
                .message("Role changed successfully!")
                .data(userForUser)
                .build();
  }

  public StandardResponse<UserEntity> getUserById(UUID id){
        UserEntity userEntity = userRepository.getById(id);
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
        return StandardResponse.<UserEntity>builder()
                .status(Status.SUCCESS)
                .message("User get!")
                .data(userEntity)
                .build();
  }
}
