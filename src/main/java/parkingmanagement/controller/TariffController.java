package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkingmanagement.domain.dto.tariff.TariffCreateDto;
import parkingmanagement.domain.dto.tariff.TariffForUser;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.TariffService;

import java.security.Principal;

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
}
