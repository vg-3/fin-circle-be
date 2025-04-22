package com.varshith.fin_circle.dto.chitfund.request;

import java.util.Set;

public record ChitFundMemberAdditionRequest(
        Set<Integer> userIds
) {
}
