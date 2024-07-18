package com.sv_profi.test.models;

import com.sv_profi.test.states.DeliveryType;
import com.sv_profi.test.states.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_order")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "recipient_name", nullable = false)
    String recipientName;

    @Column(name = "delivery_type", nullable = false)
    @Enumerated(EnumType.STRING)
    DeliveryType deliveryType;

    @Column(name = "index_recipient", nullable = false)
    Integer indexRecipient;

    @Column(name = "recipient_address",nullable = false)
    String recipientAddress;

    @OneToMany(mappedBy = "postOrder", cascade = CascadeType.ALL)
    List<Movement> displacementHistory = new ArrayList<>();

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
}
