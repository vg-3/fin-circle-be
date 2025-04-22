package com.varshith.fin_circle.dto.chitfund.response;

import com.varshith.fin_circle.dto.chitfund.ChitFundMemberDto;
import com.varshith.fin_circle.enumeration.ChitFundStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ChitFundResponse(
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
        String coOwner,
        List<ChitFundMemberDto> members
) {
}