package com.sv_profi.test.states;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryType {
    LETTER("latter"),
    PARCEL("parcel"),
    WRAPPER("wrapper"),
    POSTCARD("postcard"),
    STANDARD("standard");;

    private final String description;
}