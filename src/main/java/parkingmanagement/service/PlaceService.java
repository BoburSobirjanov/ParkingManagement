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
import parkingmanagement.exception.DataHasAlreadyExistException;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.NotAcceptableException;
import parkingmanagement.exception.UserBadRequestException;
import parkingmanagement.repository.OrderRepository;
import parkingmanagement.repository.PlaceRepository;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    public StandardResponse<PlaceForUser> save(PlaceCreateDto placeCreateDto){
        checkHasPlace(placeCreateDto.getPlace(), placeCreateDto.getFloor());
        PlaceEntity placeEntity = modelMapper.map(placeCreateDto, PlaceEntity.class);
        placeEntity.setFloor(placeCreateDto.getFloor());
        placeEntity.setPlace(placeCreateDto.getPlace());
        placeEntity.setStatus(PlaceStatus.EMPTY);
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

    public StandardResponse<String> delete(UUID placeId){
        PlaceEntity placeEntity = placeRepository.findById(placeId)
                .orElseThrow(()-> new DataNotFoundException("Place not found!"));
        placeEntity.setStatus(PlaceStatus.NOT_WORK);
        placeRepository.save(placeEntity);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("The place is temporarily unavailable!")
                .data("Place has deleted successfully!")
                .build();
    }
}
