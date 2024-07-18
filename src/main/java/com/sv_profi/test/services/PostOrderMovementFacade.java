package com.sv_profi.test.services;

import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.MovementDto;
import com.sv_profi.test.models.dto.MovementHistoryDto;
import com.sv_profi.test.models.dto.PostOfficeDto;
import com.sv_profi.test.models.dto.PostOrderDto;
import com.sv_profi.test.models.mappers.MovementMapper;
import com.sv_profi.test.models.mappers.PostOfficeMapper;
import com.sv_profi.test.models.mappers.PostOrderMapper;
import com.sv_profi.test.states.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOrderMovementFacade {

    final PostOrderService postOrderService;
    final MovementService movementService;

    public List<MovementDto> findAllByOrderId(Long orderId) {
        Optional<PostOrderDto> postOrderDto = postOrderService.findPostOrderById(orderId);
        if (postOrderDto.isPresent()) {
            return movementService.findAllByPostOrder(postOrderDto.get());
        }
        return List.of();
    }

    public List<MovementHistoryDto> findAllHistoryByPostOrder(Long orderId) {
        Optional<PostOrderDto> postOrderDto = postOrderService.findPostOrderById(orderId);
        if (postOrderDto.isPresent()) {
            List<Movement> movements = postOrderDto.get().getDisplacementHistory().stream()
                    .map(MovementMapper.INSTANCE::toEntity)
                    .toList();
            return movements.stream()
                    .map(this::toMovementHistoryDto)
                    .toList();
        }
        return List.of();
    }

    @Transactional
    public Optional<PostOrderDto> departingOrder(Long orderId, PostOfficeDto departingPoint) {
        Optional<PostOrderDto> departingOrder = postOrderService.findPostOrderById(orderId);
        if (departingOrder.isPresent()) {
            PostOrder order = PostOrderMapper.INSTANCE.toEntity(departingOrder.get());
            order.setOrderStatus(OrderStatus.DELIVERING);

            Movement movement = new Movement();
            movement.setPostOrder(order);
            movement.setPostOffice(PostOfficeMapper.INSTANCE.toEntity(departingPoint));

            order.getDisplacementHistory().add(movement);
            Optional<PostOrderDto> savedOrder = postOrderService
                    .savePostOrder(PostOrderMapper.INSTANCE.toDto(order));

            movementService.save(MovementMapper.INSTANCE.toDto(movement));

            return savedOrder;
        }
        throw new NoSuchElementException("Order with id:" + orderId + " not found");
    }

    private MovementHistoryDto toMovementHistoryDto(Movement movement) {
        if (movement == null) {
            return null;
        }
        return new MovementHistoryDto(
                movement.getId(),
                PostOrderMapper.INSTANCE.toDto(movement.getPostOrder()),
                PostOfficeMapper.INSTANCE.toDto(movement.getPostOffice())
        );
    }
}
