package parkingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingmanagement.domain.dto.order.OrderCreateDto;
import parkingmanagement.domain.dto.order.OrderForUser;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.service.OrderService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/{placeId}/save")
    @PreAuthorize(value = "hasRole('EMPLOYER')")
    public StandardResponse<OrderForUser> save(
            @RequestBody OrderCreateDto orderCreateDto,
            @PathVariable UUID placeId
            ){
        return orderService.save(orderCreateDto,placeId);
    }

    @PutMapping("/close-order")
    @PreAuthorize(value = "hasRole('EMPLOYER')")
    public StandardResponse<OrderForUser> close(
            @RequestParam String carNumber
    ){
        return orderService.closeOrder(carNumber);
    }
}
