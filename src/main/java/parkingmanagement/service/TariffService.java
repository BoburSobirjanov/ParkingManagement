package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.tariff.TariffCreateDto;
import parkingmanagement.domain.dto.tariff.TariffForUser;
import parkingmanagement.domain.entity.tariff.TariffEntity;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.TariffRepository;
import parkingmanagement.repository.UserRepository;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public StandardResponse<TariffForUser> save(TariffCreateDto tariffCreateDto, Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
      checkHasTariff(tariffCreateDto.getName(),tariffCreateDto.getDuration());
        TariffEntity tariffEntity = modelMapper.map(tariffCreateDto, TariffEntity.class);
        tariffEntity.setName(tariffCreateDto.getName());
        tariffEntity.setPrice(tariffCreateDto.getPrice());
        tariffEntity.setDuration(tariffCreateDto.getDuration());
        tariffEntity.setCreated_by(userEntity.getId());
        TariffEntity tariff = tariffRepository.save(tariffEntity);
        TariffForUser tariffForUser=modelMapper.map(tariff, TariffForUser.class);
        return StandardResponse.<TariffForUser>builder()
                .status(Status.SUCCESS)
                .message("Tariff created successfully!")
                .data(tariffForUser)
                .build();
    }
    public void checkHasTariff(String name, Double duration){
    if (tariffRepository.findTariffEntityByName(name).isPresent() && tariffRepository.findTariffEntityByDuration(duration).isPresent()){
        throw new UserBadRequestException("Tariff has already exist!");
    }
    }
}
