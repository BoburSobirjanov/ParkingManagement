package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.order.OrderCreateDto;
import parkingmanagement.domain.dto.order.OrderForUser;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/{placeId}/save")
    @PreAuthorize(value = "hasRole('EMPLOYER')or hasRole('ADMIN')")
    public StandardResponse<OrderForUser> save(
            @RequestBody OrderCreateDto orderCreateDto,
            @PathVariable UUID placeId,
            Principal principal
            ){
        return orderService.save(orderCreateDto,placeId,principal);
    }

    @PutMapping("/close-order")
    @PreAuthorize(value = "hasRole('EMPLOYER')or hasRole('ADMIN')")
    public StandardResponse<OrderForUser> close(
            @RequestParam String carNumber
    ){
        return orderService.closeOrder(carNumber);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('OWNER')")
    public StandardResponse<OrderForUser> delete(
            @PathVariable UUID id,
            Principal principal
    ){
        return orderService.delete(id,principal);
    }

    @PostMapping("/get-car-orders")
    public List<OrderForUser> getCarOrders(
            @RequestParam String number
    ){
        return orderService.getCarOrders(number);
    }
}
