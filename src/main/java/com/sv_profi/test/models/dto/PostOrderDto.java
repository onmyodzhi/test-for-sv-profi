package com.sv_profi.test.models.dto;

import com.sv_profi.test.models.PostOffice;
import com.sv_profi.test.states.DeliveryType;
import com.sv_profi.test.states.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOrderDto {
    Long id;
    String recipientName;
    DeliveryType deliveryType;
    Integer indexRecipient;
    String recipientAddress;
    List<MovementDto> displacementHistory;
    OrderStatus orderStatus;
}
