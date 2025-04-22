package com.varshith.fin_circle.dto.chitfund.request;

import java.time.Instant;
import java.util.List;

public record ChitFundCreateRequest(
        String name,
        double totalAmount,
        double monthlyPayable,
        int totalMonths,
        int currentMonth,
        double cutOffAmount,
        double interestRate,
        double savedPool,
        String status,
        Instant createdAt,
        Integer ownerId,
        Integer coOwnerId,
        List<Integer> chitFundMembers
) {
}
