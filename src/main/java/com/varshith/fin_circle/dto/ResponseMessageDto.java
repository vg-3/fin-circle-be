package com.varshith.fin_circle.dto;

import lombok.Builder;

@Builder
public record ResponseMessageDto(
        String message
) {
}
