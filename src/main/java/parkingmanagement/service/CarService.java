package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import parkingmanagement.domain.dto.car.CarCreateDto;
import parkingmanagement.domain.dto.car.CarForUser;
import parkingmanagement.domain.entity.cars.CarEntity;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.exception.DataHasAlreadyExistException;
import parkingmanagement.exception.NotAcceptableException;
import parkingmanagement.exception.RequestValidationException;
import parkingmanagement.repository.CarRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

  public CarForUser save(CarCreateDto carCreateDto, BindingResult bindingResult, Principal principal){
      if (bindingResult.hasErrors()) {
          List<ObjectError> errors = bindingResult.getAllErrors();
          throw new RequestValidationException(errors);
      }
      if (!checkCarNumber(carCreateDto.getNumber())){
        log.error("This car has already exist!");
        throw new DataHasAlreadyExistException("Car has already exist!");
      }
      CarEntity carEntity = modelMapper.map(carCreateDto, CarEntity.class);
      carEntity.setNumber(carCreateDto.getNumber());
      try {
          carEntity.setType(CarType.valueOf(String.valueOf(carCreateDto.getType())));
      }catch (Exception e){
         throw new NotAcceptableException("Invalid car type!");
      }
      return modelMapper.map(carRepository.save(carEntity), CarForUser.class);
  }
  public boolean checkCarNumber(String number){
      CarEntity carEntity = carRepository.findCarEntityByNumber(number);
      return carEntity != null;
  }
}
