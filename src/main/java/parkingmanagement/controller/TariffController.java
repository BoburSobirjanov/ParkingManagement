package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.tariff.TariffCarDto;
import parkingmanagement.domain.dto.tariff.TariffCreateDto;
import parkingmanagement.domain.dto.tariff.TariffForUser;
import parkingmanagement.domain.entity.tariff.Tariff_Car;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.TariffService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tariff")
public class TariffController {
    private final TariffService tariffService;

    @PostMapping("/save")
    @PreAuthorize(value = "hasRole('OWNER') or hasRole('ADMIN')")
    public StandardResponse<TariffForUser> save(
            @RequestBody TariffCreateDto tariffCreateDto,
            Principal principal
            ){
        return tariffService.save(tariffCreateDto,principal);
    }

    @PostMapping("/assign-to-car")
    public StandardResponse<Tariff_Car> assignToCar(
            @RequestBody TariffCarDto tariffCarDto,
            Principal principal
            ){
        return tariffService.assignTariffToCar(tariffCarDto,principal);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<TariffForUser> delete(
            @PathVariable UUID id,
            Principal principal
            ){
        return tariffService.delete(id,principal);
    }

    @GetMapping("/get-all")
    public List<TariffForUser> getAll(){
        return tariffService.getAll();
    }
}
