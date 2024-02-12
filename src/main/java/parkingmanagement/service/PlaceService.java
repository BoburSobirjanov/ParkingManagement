package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.place.PlaceCreateDto;
import parkingmanagement.domain.dto.place.PlaceForUser;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceStatus;
import parkingmanagement.domain.entity.place.PlaceType;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.NotAcceptableException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.PlaceRepository;
import parkingmanagement.repository.UserRepository;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public StandardResponse<PlaceForUser> save(PlaceCreateDto placeCreateDto, Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        checkHasPlace(placeCreateDto.getPlace(), placeCreateDto.getFloor());
        PlaceEntity placeEntity = modelMapper.map(placeCreateDto, PlaceEntity.class);
        placeEntity.setFloor(placeCreateDto.getFloor());
        placeEntity.setCreated_by(userEntity.getId());
        placeEntity.setPlace(placeCreateDto.getPlace());
        placeEntity.setStatus(PlaceStatus.EMPTY);
        placeEntity.setCreated_by(userEntity.getId());
        try {
            placeEntity.setType(PlaceType.valueOf(String.valueOf(placeCreateDto.getType())));
        }catch (Exception e){
            throw new NotAcceptableException("Invalid place type!");
        }
        PlaceEntity save = placeRepository.save(placeEntity);
        PlaceForUser placeForUser = modelMapper.map(save, PlaceForUser.class);
        return StandardResponse.<PlaceForUser>builder()
                .status(Status.SUCCESS)
                .message("Successfully created")
                .data(placeForUser)
                .build();
    }
    public void checkHasPlace(String place, String floor){
        if (placeRepository.findPlaceEntityByPlace(place).isPresent() && placeRepository.findPlaceEntityByFloor(floor).isPresent()) {
            throw new UserBadRequestException("Place has already exists");
        }
    }

    public StandardResponse<String> delete(UUID placeId,Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        PlaceEntity placeEntity = placeRepository.findPlaceEntityById(placeId);
        if (placeEntity==null){
            throw new DataNotFoundException("Place not found!");
        }
        placeEntity.setStatus(PlaceStatus.NOT_WORK);
        placeEntity.setDeleted_by(userEntity.getId());
        placeEntity.setDeleted_time(LocalDateTime.now());
        placeEntity.set_deleted(true);
        placeRepository.save(placeEntity);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("The place is temporarily unavailable!")
                .data("Place has deleted successfully!")
                .build();
    }

    public List<PlaceForUser> getAll(){
       List<PlaceForUser> placeEntities = placeRepository.getAll();
       if (placeEntities==null){
           throw new DataNotFoundException("Places not found!");
       }
       return placeEntities;
    }

    public List<PlaceEntity> getPlaceByType(PlaceType type){
        List<PlaceEntity> placeEntities = placeRepository.findPlaceEntityByType(type);
        if (placeEntities==null){
            log.error("Place not found same this type! Please, try again or check type");
            throw new DataNotFoundException("Not found!");
        }
        return placeRepository.findPlaceEntityByType(type);
    }
}
