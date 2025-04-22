package com.varshith.fin_circle.dto.chitfund.response;

import com.varshith.fin_circle.dto.AddressDto;
import com.varshith.fin_circle.dto.chitfund.GuarantorDto;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChitFundMemberResponse(
        Integer userId,
        String name,
        String email,
        String phoneNumber,
        LocalDate dob,
        String imageUrl,
        String aadhaarNumber,
        String panNumber,
        String kycDoc,
        AddressDto address,
        boolean hasWon,
        double winningAmount,
        int winningMonth,
        int monthsPaid,
        double totalPaid,
        GuarantorDto guarantor
) {
}
