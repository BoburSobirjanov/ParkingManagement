package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.place.PlaceCreateDto;
import parkingmanagement.domain.dto.place.PlaceForUser;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.PlaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/save")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public StandardResponse<PlaceForUser> save(
            @RequestBody PlaceCreateDto placeCreateDto
            ){
        return placeService.save(placeCreateDto);
    }

    @DeleteMapping("/{placeId}/delete")
    @PreAuthorize(value = "(hasRole('ADMIN'))")
    public StandardResponse<String> delete(
            @PathVariable UUID placeId
            ){
     return placeService.delete(placeId);
    }

    @GetMapping("/get-all")
    public List<PlaceEntity> getAllPlace(){
        return placeService.getAll();
    }

    @PostMapping("/get-by-type")
    public List<PlaceEntity> getByType(
            @RequestParam String type
    ){
        return placeService.getPlaceByType(type);
    }
}
