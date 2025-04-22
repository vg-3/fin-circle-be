package com.varshith.fin_circle.dto.chitfund.response;

import lombok.Builder;

@Builder
public record ChitFundCreateResponseDto(
       Integer id,
       String name,
       String message
) {
}
