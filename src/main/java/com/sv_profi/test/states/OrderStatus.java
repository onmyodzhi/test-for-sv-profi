package com.sv_profi.test.states;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    REGISTERED("registered"),
    DELIVERING("delivering"),
    IN_TRANSIT("in transit"),
    ARRIVED("arrived"),
    RECEIVED("received");

    private final String label;
}
