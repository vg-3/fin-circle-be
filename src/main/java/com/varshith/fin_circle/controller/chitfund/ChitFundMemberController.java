package com.varshith.fin_circle.controller.chitfund;

import com.varshith.fin_circle.dto.chitfund.request.ChitFundMemberAdditionRequest;
import com.varshith.fin_circle.dto.chitfund.request.ChitFundMemberRemovalRequest;
import com.varshith.fin_circle.dto.chitfund.request.UpdateGuarantorRequest;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundMemberResponse;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundResponse;
import com.varshith.fin_circle.service.ChitFundMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/chit-fund/members")
@RequiredArgsConstructor
public class ChitFundMemberController {

    private final ChitFundMemberService chitFundMemberService;

//    add user to chit fund
    @PatchMapping("/{chitFundId}/add")
    public ChitFundResponse addMember(@PathVariable Integer chitFundId,
                                      @RequestBody ChitFundMemberAdditionRequest chitFundMemberAdditionRequest){
        return chitFundMemberService.addMember(chitFundId, chitFundMemberAdditionRequest.userIds());
    }

    @PatchMapping("/{chitFundId}/remove")
    public ChitFundResponse removeMember(@PathVariable Integer chitFundId,
                                         @RequestBody ChitFundMemberRemovalRequest chitFundMemberRemovalRequest){
        return chitFundMemberService.removeMember(chitFundId, chitFundMemberRemovalRequest.memberIds());
    }

//    update guarantor
    @PatchMapping("/update-guarantor")
    public ChitFundMemberResponse updateGuarantor(@RequestBody UpdateGuarantorRequest updateGuarantorRequest){
      return chitFundMemberService.updateGuarantor(updateGuarantorRequest);
    }

}


