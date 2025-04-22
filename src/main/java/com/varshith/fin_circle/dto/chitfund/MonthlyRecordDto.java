package com.varshith.fin_circle.dto.chitfund;

import com.varshith.fin_circle.enumeration.MONTHLY_RECORD_STATUS;
import lombok.Builder;

import java.time.Instant;

@Builder
public record MonthlyRecordDto(
         Integer id,
         int monthNumber,
         MONTHLY_RECORD_STATUS status,
         Instant auctionDateTime,
         float totalCollectedAmount,
         float amountSaved,
         Boolean isCompleted
) {
}
