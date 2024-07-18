package com.sv_profi.test.controllers;

import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.*;
import com.sv_profi.test.services.PostOrderMovementFacade;
import com.sv_profi.test.services.PostOrderService;
import com.sv_profi.test.states.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post-order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOrderController {

    final PostOrderService postOrderService;
    final PostOrderMovementFacade postOrderMovementFacade;

    @PostMapping
    public ResponseEntity<PostOrderDto> registeredPostOrder(@RequestBody PostOrderDto postOrderDto) {
        Optional<PostOrderDto> orderForCreate = postOrderService.registeredOrder(postOrderDto);
        if (orderForCreate.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(orderForCreate.get().getId())
                    .toUri();

            return ResponseEntity.created(location).body(orderForCreate.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostOrderDto> registeredPostOrder(@PathVariable Long id) {
        Optional<PostOrderDto> order = postOrderService.findPostOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/arrive")
    public ResponseEntity<PostOrderDto> arrivePostOrder(@PathVariable Long id, @RequestBody PostOfficeDto arrivedPoint) {
        Optional<PostOrderDto> updatedOrder = postOrderService.arrivedOrder(id, arrivedPoint);
        return updatedOrder.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/delivery")
    public ResponseEntity<PostOrderDto> deliveringPostOrder(@PathVariable Long id, @RequestBody PostOfficeDto deliveredPoint) {
        Optional<PostOrderDto> updatedOrder = postOrderMovementFacade.departingOrder(id, deliveredPoint);
        return updatedOrder.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/received")
    public ResponseEntity<PostOrderDto> receivedPostOrder(@PathVariable Long id, @RequestBody ReceiverDto receiverDto) {
        Optional<PostOrderDto> postOrderForPickup = postOrderService.receivingOrder(id, receiverDto);
        return postOrderForPickup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/status")
    public ResponseEntity<OrderStatus> getPostOrderStatus(@PathVariable Long id) {
        Optional<OrderStatus> orderStatus = postOrderService.getStatusOfOrder(id);
        return orderStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<MovementHistoryDto>> getPostOrderHistory(@PathVariable Long id) {
        List<MovementHistoryDto> history = postOrderMovementFacade.findAllHistoryByPostOrder(id);
        if (!history.isEmpty()) {
            return ResponseEntity.ok().body(history);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
