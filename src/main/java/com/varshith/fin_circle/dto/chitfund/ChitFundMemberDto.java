package com.varshith.fin_circle.dto.chitfund;

import lombok.Builder;

@Builder
public record ChitFundMemberDto(
        Integer userId,
        Integer memberId,
        String name,
        boolean hasWon,
        double winningAmount,
        int winningMonth,
        int monthsPaid,
        double totalPaid
        ) {
}
