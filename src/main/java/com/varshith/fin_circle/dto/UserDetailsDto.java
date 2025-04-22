package com.varshith.fin_circle.dto;

import java.time.LocalDate;

public record UserDetailsDto(LocalDate DOB, String aadhaarNumber, String panNumber, String kycDoc, AddressDto address) {
}
