package com.sv_profi.test.services;

import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.PostOfficeDto;
import com.sv_profi.test.models.dto.PostOrderDto;
import com.sv_profi.test.models.dto.ReceiverDto;
import com.sv_profi.test.models.mappers.PostOrderMapper;
import com.sv_profi.test.repositories.PostOrderRepository;
import com.sv_profi.test.states.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOrderService {
    final PostOrderRepository orderRepository;

    public List<PostOrderDto> findAllPostOrders() {
        List<PostOrder> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return new ArrayList<>();
        }
        return orders.stream()
                .map(PostOrderMapper.INSTANCE::toDto)
                .toList();
    }

    public Optional<PostOrderDto> findPostOrderById(Long id) {
        return orderRepository.findById(id)
                .map(PostOrderMapper.INSTANCE::toDto);
    }

    public Optional<PostOrderDto> registeredOrder(PostOrderDto postOrderDto) {
        if (postOrderDto.getId() == null) {
            PostOrder order = PostOrderMapper.INSTANCE.toEntity(postOrderDto);
            order.setOrderStatus(OrderStatus.REGISTERED);
            PostOrder savedOrder = orderRepository.save(order);
            return Optional.of(PostOrderMapper.INSTANCE.toDto(savedOrder));
        }
        throw new RuntimeException("Order with id " + postOrderDto.getId() + " already exists");
    }

    @Transactional
    public Optional<PostOrderDto> arrivedOrder(Long orderId, PostOfficeDto arrivedPoint) {
        Optional<PostOrder> arrivedOrder = orderRepository.findById(orderId);

        if (arrivedOrder.isPresent()) {
            PostOrder order = arrivedOrder.get();

            if (order.getIndexRecipient().equals(arrivedPoint.getIndex())) {
                order.setOrderStatus(OrderStatus.ARRIVED);
            } else {
                order.setOrderStatus(OrderStatus.IN_TRANSIT);
            }

            order = orderRepository.save(order);

            return Optional.of(PostOrderMapper.INSTANCE.toDto(order));
        }
        throw new NoSuchElementException("Order with id:" + orderId + " not found");
    }

    @Transactional
    public Optional<PostOrderDto> receivingOrder(Long orderId, ReceiverDto receiverDto) {
        Optional<PostOrder> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            String nameOfRegisteredReceiver = order.get().getRecipientName();
            String nameOfReceiver = receiverDto.getReceiverName();
            if (nameOfRegisteredReceiver.equalsIgnoreCase(nameOfReceiver)) {
                order.get().setOrderStatus(OrderStatus.RECEIVED);
                orderRepository.save(order.get());
                return Optional.of(PostOrderMapper.INSTANCE.toDto(order.get()));
            } else throw new NoSuchElementException("Name of receiver of the order is not correct");
        }
        throw new NoSuchElementException("Order with id:" + orderId + " not found");
    }

    public Optional<OrderStatus> getStatusOfOrder(Long orderId) {
        Optional<PostOrder> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            PostOrder existingOrder = order.get();
            return Optional.of(existingOrder.getOrderStatus());
        }
        throw new NoSuchElementException("Order with id:" + orderId + " not found");
    }

    public Optional<PostOrderDto> updatePostOrder(PostOrderDto postOrderDto) {
        PostOrder order = PostOrderMapper.INSTANCE.toEntity(postOrderDto);
        if (order.getId() != null) {
            PostOrder savedOrder = orderRepository.save(order);
            return Optional.of(PostOrderMapper.INSTANCE.toDto(savedOrder));
        }
        throw new NoSuchElementException("Post order does not exist");
    }

    public Optional<PostOrderDto> savePostOrder(PostOrderDto postOrderDto) {
        PostOrder order = PostOrderMapper.INSTANCE.toEntity(postOrderDto);
        order = orderRepository.save(order);
        return Optional.of(PostOrderMapper.INSTANCE.toDto(order));
    }

    public void deletePostOrderById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        }
    }
}

