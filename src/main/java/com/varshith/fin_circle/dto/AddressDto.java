package com.varshith.fin_circle.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        String apartmentName,
        String flatNumber,
        String street,
        String landmark,
        String city,
        String state,
        Integer pinCode) {
}