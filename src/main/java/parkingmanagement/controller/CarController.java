package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkingmanagement.domain.dto.car.CarCreateDto;
import parkingmanagement.domain.dto.car.CarForUser;
import parkingmanagement.service.CarService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    @PostMapping("/save")
    public ResponseEntity<CarForUser> save (
            @RequestBody CarCreateDto carCreateDto,
            BindingResult bindingResult,
            Principal principal){
     return ResponseEntity.ok(carService.save(carCreateDto,bindingResult,principal));
    }
}
