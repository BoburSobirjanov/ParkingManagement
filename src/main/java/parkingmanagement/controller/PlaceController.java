package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.place.PlaceCreateDto;
import parkingmanagement.domain.dto.place.PlaceForUser;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceType;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.PlaceService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/save")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<PlaceForUser> save(
            @RequestBody PlaceCreateDto placeCreateDto,
            Principal principal
            ){
        return placeService.save(placeCreateDto,principal);
    }

    @DeleteMapping("/{placeId}/delete")
    @PreAuthorize(value = "(hasRole('ADMIN')or hasRole('OWNER'))")
    public StandardResponse<String> delete(
            @PathVariable UUID placeId,
            Principal principal
            ){
     return placeService.delete(placeId,principal);
    }

    @GetMapping("/get-all")
    public List<PlaceForUser> getAllPlace(){
        return placeService.getAll();
    }

    @PostMapping("/get-by-type")
    public List<PlaceEntity> getByType(
            @RequestParam PlaceType type
    ){
        return placeService.getPlaceByType(type);
    }
}
