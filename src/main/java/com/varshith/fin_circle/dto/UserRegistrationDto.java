package com.varshith.fin_circle.dto;

import com.varshith.fin_circle.entity.UserDetails;

public record UserRegistrationDto(UserDto user, UserDetails userDetails) {
}


