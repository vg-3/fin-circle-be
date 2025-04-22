package com.varshith.fin_circle.dto.chitfund.request;

//request format for updating guarantor
public record UpdateGuarantorRequest(
        Integer memberId,
        Integer guarantorMemberId
) {
}
