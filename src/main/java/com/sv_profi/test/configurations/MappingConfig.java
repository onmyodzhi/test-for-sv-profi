package com.sv_profi.test.configurations;

import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.MovementDto;
import com.sv_profi.test.models.dto.PostOrderDto;
import com.sv_profi.test.models.mappers.MovementMapper;
import com.sv_profi.test.models.mappers.PostOrderMapper;
import com.sv_profi.test.services.MovementService;
import com.sv_profi.test.services.PostOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingConfig {

    PostOrderService postOrderService;

    MovementService movementService;

    @AfterMapping
    public void savePostOrderWithMovements(PostOrderDto postOrder, @MappingTarget PostOrderDto postOrderDto) {
        List<MovementDto> movementDto = postOrder.getDisplacementHistory();

        saveMovement(movementDto);

        postOrderService.savePostOrder(postOrderDto);
    }

    @AfterMapping
    public void saveMovementWithPostOrders(MovementDto movement, @MappingTarget MovementDto movementDto) {
        movementService.save(movement);
    }

    private void saveMovement(List<MovementDto> movement) {
        movementService.saveAll(movement);
    }
}
