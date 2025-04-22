package com.varshith.fin_circle.dto.chitfund;

import lombok.Builder;

@Builder
public record GuarantorDto(
        String name,
        String imageUrl
) {
}
