package parkingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import parkingmanagement.domain.dto.order.OrderCreateDto;
import parkingmanagement.domain.dto.order.OrderForUser;
import parkingmanagement.domain.entity.cars.CarType;
import parkingmanagement.domain.entity.orders.OrderEntity;
import parkingmanagement.domain.entity.orders.PaymentMethod;
import parkingmanagement.domain.entity.orders.OrderStatus;
import parkingmanagement.domain.entity.place.PlaceEntity;
import parkingmanagement.domain.entity.place.PlaceStatus;
import parkingmanagement.domain.entity.user.UserEntity;
import parkingmanagement.exception.DataNotFoundException;
import parkingmanagement.exception.NotAcceptableException;
import parkingmanagement.repository.*;
import parkingmanagement.response.StandardResponse;
import parkingmanagement.response.Status;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public StandardResponse<OrderForUser> save(OrderCreateDto orderCreateDto, UUID placeId, Principal principal){
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        PlaceEntity placeEntity = placeRepository.findPlaceEntityById(placeId);
        OrderEntity order = orderRepository.findOrderEntityByCarNumberAndStatus(orderCreateDto.getCar_number());
        if (order!=null){
            log.error("This car is placed in another place. Please complete the order first!");
            throw new NotAcceptableException("Unavailable action! Please, check this car's orders!");
        }
        if (placeEntity==null){
            log.error("Place not found!");
            throw new DataNotFoundException("Place not found!");
        }
        if (!checkTypes(String.valueOf(placeEntity.getType()), orderCreateDto.getType())){
            log.error("Place and car types are not the same!");
            throw new NotAcceptableException("Place and car types are not the same");
        }
        if (placeEntity.getStatus() == PlaceStatus.BUSY){
            log.error("Place is busy now!");
            throw new NotAcceptableException("Place is not available now!");
        }
        OrderEntity orderEntity = modelMapper.map(orderCreateDto, OrderEntity.class);
        placeEntity.setStatus(PlaceStatus.BUSY);
        orderEntity.setPlace_id(placeEntity);
        orderEntity.setType(CarType.valueOf(orderCreateDto.getType()));
        orderEntity.setStatus(OrderStatus.PROGRESS);
        orderEntity.setCar_number(orderCreateDto.getCar_number());
        orderEntity.setStart_time(LocalDateTime.now());
        orderEntity.setCreated_by(user.getId());
        orderRepository.save(orderEntity);
        OrderForUser orderForUser = modelMapper.map(orderEntity,OrderForUser.class);
        return StandardResponse.<OrderForUser>builder()
                .status(Status.SUCCESS)
                .message("Order created successfully!")
                .data(orderForUser)
                .build();
    }
    public StandardResponse<OrderForUser> closeOrder(String carNumber){
      OrderEntity orderEntity=orderRepository.findOrderEntityByCarNumberAndStatus(carNumber);
      if (orderEntity==null){
          log.error("Order not found!");
          throw new DataNotFoundException("Order not found same this or this order is busy now!");
      }
        orderEntity.setEnd_time(LocalDateTime.now());
        double duration=ChronoUnit.MINUTES.between(orderEntity.getStart_time(),orderEntity.getEnd_time());
        PlaceEntity placeEntity = placeRepository.findPlaceEntityById(orderEntity.getPlace_id().getId());
        placeEntity.setStatus(PlaceStatus.EMPTY);
        orderEntity.setAmount(duration*orderEntity.getType().getAmount());
        orderEntity.setStatus(OrderStatus.COMPLETED);
        orderEntity.setMethod(PaymentMethod.CASH);
        orderRepository.save(orderEntity);
        OrderForUser orderForUser = modelMapper.map(orderEntity, OrderForUser.class);
        return StandardResponse.<OrderForUser>builder()
                .status(Status.SUCCESS)
                .message("Order closed successfully!")
                .data(orderForUser)
                .build();
    }
    public boolean checkTypes(String type1, String type2){
        return type1.equals(type2);
    }

    public StandardResponse<OrderForUser> delete(UUID orderId, Principal principal){
        OrderEntity orderEntity= orderRepository.findOrderEntityById(orderId);
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        orderEntity.set_deleted(true);
        orderEntity.setDeleted_by(userEntity.getId());
        orderEntity.setDeleted_time(LocalDateTime.now());
        OrderForUser orderForUser= modelMapper.map(orderEntity, OrderForUser.class);
        return StandardResponse.<OrderForUser>builder()
                .status(Status.SUCCESS)
                .message("Order deleted!")
                .data(orderForUser)
                .build();
    }

    public List<OrderForUser> getCarOrders(String number){
        List<OrderForUser> orderForUsers = orderRepository.findOrderEntityByCarNumber(number);
        if (orderForUsers==null){
            throw new DataNotFoundException("Orders not found!");
        }
        return orderForUsers;
    }

}
