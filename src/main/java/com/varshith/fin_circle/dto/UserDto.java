package com.varshith.fin_circle.dto;


public record UserDto(
        String email,
        String password,
        String name,
        String phoneNumber) {
}
