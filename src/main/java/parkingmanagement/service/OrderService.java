package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.order.OrderCreateDto;
import parkingmanagement.domain.dto.order.OrderForUser;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.orders.OrderEntity;
import parkingmanagement.domain.entity.orders.OrderStatus;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceStatus;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.NotAcceptableException;
import parkingmanagement.repository.OrderRepository;
import parkingmanagement.repository.PlaceRepository;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;

    public StandardResponse<OrderForUser> save(OrderCreateDto orderCreateDto,UUID placeId){
        PlaceEntity placeEntity = placeRepository.findPlaceEntityById(placeId);
        if (placeEntity==null){
            log.error("Place not found!");
            throw new DataNotFoundException("Place not found!");
        }
        if (placeEntity.getStatus() == PlaceStatus.BUSY){
            log.error("Place is busy now!");
            throw new NotAcceptableException("Place is not available now!");
        }
        OrderEntity orderEntity = modelMapper.map(orderCreateDto, OrderEntity.class);
        orderEntity.setType(CarType.valueOf(orderCreateDto.getType()));
        orderEntity.setStatus(OrderStatus.PROGRESS);
        orderEntity.setCarNumber(orderCreateDto.getCarNumber());
        orderEntity.setStartTime(LocalDateTime.now());
        OrderForUser orderForUser = modelMapper.map(orderEntity,OrderForUser.class);
        orderRepository.save(orderEntity);
        return StandardResponse.<OrderForUser>builder()
                .status(Status.SUCCESS)
                .message("Order created successfully!")
                .data(orderForUser)
                .build();
    }
    public StandardResponse<OrderForUser> closeOrder(String carNumber){
      OrderEntity orderEntity=orderRepository.findOrderEntityByCarNumberAndStatus(carNumber,OrderStatus.PROGRESS);
      if (orderEntity==null){
          log.error("Order not found!");
          throw new DataNotFoundException("Order not found same this!");
      }
        orderEntity.setEndTime(LocalDateTime.now());
        double duration=ChronoUnit.MINUTES.between(orderEntity.getStartTime(),orderEntity.getEndTime());
        orderEntity.setAmount(duration*orderEntity.getType().getAmount());
        orderEntity.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orderEntity);
        OrderForUser orderForUser = modelMapper.map(orderEntity, OrderForUser.class);
        return StandardResponse.<OrderForUser>builder()
                .status(Status.SUCCESS)
                .message("Order closed successfully!")
                .data(orderForUser)
                .build();
    }
}
