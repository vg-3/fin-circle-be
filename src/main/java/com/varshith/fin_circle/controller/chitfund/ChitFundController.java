package com.varshith.fin_circle.controller.chitfund;

import com.varshith.fin_circle.dto.ResponseMessageDto;
import com.varshith.fin_circle.dto.chitfund.request.ChitFundCreateRequest;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundCreateResponseDto;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundResponse;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundsByUserTypeResponse;
import com.varshith.fin_circle.enumeration.ChitFundMemberType;
import com.varshith.fin_circle.service.ChitFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chit-fund")
@RequiredArgsConstructor
public class ChitFundController {

    private final ChitFundService chitFundService;

//    get chit fund by chitFundId
    @GetMapping("/{chitFundId}")
    public ChitFundResponse getChitFund(@PathVariable Integer chitFundId){
        return chitFundService.getChitFund(chitFundId);
    }

//    get chit funds by userId and type(MEMBER, OWNER, CO_OWNER)
    @GetMapping("/user/{userId}")
    public List<ChitFundsByUserTypeResponse> getChitFundsByUserIdAndType(
            @PathVariable Integer userId, @RequestParam ChitFundMemberType type){
        return chitFundService.getChitFundsByUserIdAndType(userId,type);
    }

// create chit fund
    @PostMapping()
    public ChitFundCreateResponseDto createChitFund(@RequestBody ChitFundCreateRequest chitFundCreateRequest){
        return chitFundService.createChitFund(chitFundCreateRequest);
    }

    @DeleteMapping("/{chitFundId}")
    public ResponseMessageDto deleteChitFund(@PathVariable Integer chitFundId){
        return chitFundService.deleteChitFund(chitFundId);
    }

}
