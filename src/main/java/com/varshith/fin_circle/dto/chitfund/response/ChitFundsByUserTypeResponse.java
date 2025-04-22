package com.varshith.fin_circle.dto.chitfund.response;

import com.varshith.fin_circle.enumeration.ChitFundStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ChitFundsByUserTypeResponse(
        Integer id,
        String name,
        double totalAmount,
        double monthlyPayable,
        int totalMonths,
        int currentMonth,
        double cutOffAmount,
        double interestRate,
        double savedPool,
        ChitFundStatus status,
        Instant createdAt,
        String owner,
        String coOwner
) {
}
