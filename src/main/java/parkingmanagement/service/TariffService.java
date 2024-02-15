package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.tariff.TariffCarDto;
import parkingmanagement.domain.dto.tariff.TariffCreateDto;
import parkingmanagement.domain.dto.tariff.TariffForUser;
import parkingmanagement.domain.entity.tariff.Tariff_Car;
import parkingmanagement.domain.entity.tariff.TariffEntity;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.TariffCarRepository;
import parkingmanagement.repository.TariffRepository;
import parkingmanagement.repository.UserRepository;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TariffCarRepository tariffCarRepository;

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

    public Optional<TariffEntity> getTariffByName(String name){
        return tariffRepository.findTariffEntityByName(name);
    }
    public StandardResponse<Tariff_Car> assignTariffToCar(TariffCarDto tariffCarDto,Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
         Tariff_Car tariffCar = modelMapper.map(tariffCarDto, Tariff_Car.class);
         tariffCar.setCar_number(tariffCarDto.getCar_number());
         tariffCar.setTariff_id(tariffCarDto.getTariff_id());
         tariffCar.setStart_time(LocalDateTime.now());
         TariffEntity tariffEntity = tariffRepository.getTariffEntityById(tariffCarDto.getTariff_id());
         if (tariffEntity==null){
             throw new DataNotFoundException("Tariff not found!");
         }
         tariffCar.setEnd_time(LocalDateTime.now().plusDays((tariffEntity.getDuration()).longValue()));
         tariffCar.setAssigned_by(userEntity.getId());
         tariffCarRepository.save(tariffCar);
         return StandardResponse.<Tariff_Car>builder()
                 .status(Status.SUCCESS)
                 .message("Car assigned to Tariff successfully!")
                 .data(tariffCar)
                 .build();
    }

    public StandardResponse<TariffForUser> delete(UUID tariffId,Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        TariffEntity tariff = tariffRepository.getTariffEntityById(tariffId);
        if (tariff==null){
            throw new DataNotFoundException("Tariff not found!");
        }
        tariff.set_deleted(true);
        tariff.setDeleted_time(LocalDateTime.now());
        tariff.setDeleted_by(userEntity.getId());
        TariffEntity tariff1 = tariffRepository.save(tariff);
        TariffForUser tariffForUser = modelMapper.map(tariff1, TariffForUser.class);
        return StandardResponse.<TariffForUser>builder()
                .status(Status.SUCCESS)
                .message("Tariff deleted successfully!")
                .data(tariffForUser)
                .build();
    }

    public List<TariffForUser> getAll(){
        List<TariffForUser> tariffForUsers = tariffRepository.getAll();
        if (tariffForUsers==null){
            throw new DataNotFoundException("Tariff not found!");
        }
        return tariffForUsers;
    }
}
