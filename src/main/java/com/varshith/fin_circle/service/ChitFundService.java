package com.varshith.fin_circle.service;

import com.varshith.fin_circle.dto.ResponseMessageDto;
import com.varshith.fin_circle.dto.chitfund.*;
import com.varshith.fin_circle.dto.chitfund.request.ChitFundCreateRequest;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundCreateResponseDto;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundResponse;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundsByUserTypeResponse;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.entity.chitfund.ChitFund;
import com.varshith.fin_circle.entity.chitfund.ChitFundMember;
import com.varshith.fin_circle.entity.chitfund.MonthlyRecord;
import com.varshith.fin_circle.enumeration.ChitFundMemberType;
import com.varshith.fin_circle.enumeration.ChitFundStatus;
import com.varshith.fin_circle.enumeration.MONTHLY_RECORD_STATUS;
import com.varshith.fin_circle.exception.ResourceNotFound;
import com.varshith.fin_circle.repository.ChitFundRepository;
import com.varshith.fin_circle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChitFundService {

    private final UserRepository userRepository;

    private final ChitFundRepository chitFundRepository;

//    get all chit funds



//    get by user id

    public List<ChitFundsByUserTypeResponse> getChitFundsByUserIdAndType(Integer userId, ChitFundMemberType type){
        if(!Set.of(ChitFundMemberType.OWNER, ChitFundMemberType.CO_OWNER, ChitFundMemberType.MEMBER).contains(type)){
            throw new IllegalArgumentException("Invalid type: must be OWNER, CO_OWNER, OR MEMBER");
        }
        List<ChitFund> chitFunds = switch (type){
            case OWNER -> chitFundRepository.findByOwnerId(userId);
            case CO_OWNER -> chitFundRepository.findByCoOwnerId(userId);
            case MEMBER -> chitFundRepository.findByMemberUserId(userId);
        };

        List<ChitFundsByUserTypeResponse> chitFundsByUserTypeResponse = new ArrayList<>();
        for(ChitFund chitFund : chitFunds){
        ChitFundsByUserTypeResponse chitFundResponse  = ChitFundsByUserTypeResponse.builder()
                .id(chitFund.getId())
                .name(chitFund.getName())
                .totalAmount(chitFund.getTotalAmount())
                .totalMonths(chitFund.getTotalMonths())
                .currentMonth(chitFund.getCurrentMonth())
                .cutOffAmount(chitFund.getCutOffAmount())
                .interestRate(chitFund.getInterestRate())
                .savedPool(chitFund.getSavedPool())
                .status(chitFund.getStatus())
                .createdAt(chitFund.getCreatedAt())
                .owner(chitFund.getOwner().getName())
                .coOwner(chitFund.getCoOwner().getName())
                .build();

        chitFundsByUserTypeResponse.add(chitFundResponse);
        }
        return chitFundsByUserTypeResponse;
    }

//    create
    public ChitFundCreateResponseDto createChitFund(ChitFundCreateRequest chitFundCreateRequest){

//        get owner id from request
        Integer ownerId = chitFundCreateRequest.ownerId();
//        get co-owner id from  request
        Integer coOwnerId = chitFundCreateRequest.coOwnerId();

//        throw an exception if either of them are null
        if(ownerId == null || coOwnerId == null){
            throw new IllegalArgumentException("owner or co-owner cannot be null");
        }
//      throw an exception if owner and co owner are same
        if(Objects.equals(ownerId,coOwnerId)){
            throw new IllegalArgumentException("owner and co-owner cannot be same");
        }
//        get owner details from db
        User owner = userRepository.findById(chitFundCreateRequest.ownerId())
                .orElseThrow(()-> new ResourceNotFound("Owner not found"));
//      get owner details from db
        User coOwner = userRepository.findById(chitFundCreateRequest.coOwnerId())
                .orElseThrow(()-> new ResourceNotFound("Co-owner not found"));

//        create chit fund without members
        ChitFund chitFund = ChitFund.builder()
                .name(chitFundCreateRequest.name())
                .totalAmount(chitFundCreateRequest.totalAmount())
                .monthlyPayable(chitFundCreateRequest.monthlyPayable())
                .totalMonths(chitFundCreateRequest.totalMonths())
                .currentMonth(0)
                .cutOffAmount(chitFundCreateRequest.cutOffAmount())
                .interestRate(chitFundCreateRequest.interestRate())
                .savedPool(0.0)
                .status(ChitFundStatus.ACTIVE)
                .createdAt(chitFundCreateRequest.createdAt())
                .owner(owner)
                .coOwner(coOwner)
                .build();

//        add members to chit fund without guarantor
        if(chitFundCreateRequest.chitFundMembers() != null && !chitFundCreateRequest.chitFundMembers().isEmpty()){
            List<ChitFundMember> chitFundMembers =  new ArrayList<>();
            for(Integer userId : chitFundCreateRequest.chitFundMembers()) {
                User userToBeAdded = userRepository.findById(userId)
                        .orElseThrow(()-> new ResourceNotFound("Member not found"));

                ChitFundMember member = ChitFundMember.builder()
                        .hasWon(false)
                        .monthsPaid(0)
                        .totalPaid(0)
                        .user(userToBeAdded)
                        .chitFund(chitFund)
                        .build();
                chitFundMembers.add(member);
            }
            chitFund.setMembers(chitFundMembers);
        }


//        add monthly records
        List<MonthlyRecord>  monthlyRecords = new ArrayList<>();
//        calculate total months
        for(int i=1;i<=chitFundCreateRequest.totalMonths();i++){
            MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                    .monthNumber(i)
                    .status(MONTHLY_RECORD_STATUS.TO_BE_UPDATED)
                    .isCompleted(false)
                    .chitFund(chitFund)
                    .build();
            monthlyRecords.add(monthlyRecord);
        }
        chitFund.setMonthlyRecords(monthlyRecords);
        ChitFund savedChitFund = chitFundRepository.save(chitFund);
        return  ChitFundCreateResponseDto.builder()
                .id(savedChitFund.getId())
                .name(savedChitFund.getName())
                .message("Chit fund created successfully")
                .build();
    }


//    get chit fund by id
    public ChitFundResponse getChitFund(Integer chitFundId){
        if(chitFundId == null){
            throw new IllegalArgumentException("Chit fund id cannot be null");
        }
//        get chitFund from db
       ChitFund retreviedChitFund =   chitFundRepository.findById(chitFundId)
               .orElseThrow(()-> new ResourceNotFound("Chit fund not found"));

//        construct chit fund members response
       List<ChitFundMemberDto> members = new ArrayList<>();

       for(ChitFundMember chitFundMember : retreviedChitFund.getMembers()){
           ChitFundMemberDto member = ChitFundMemberDto.builder()
                   .userId(chitFundMember.getUser().getId())
                   .memberId(chitFundMember.getId())
                   .name(chitFundMember.getUser().getName())
                   .hasWon(chitFundMember.isHasWon())
                   .winningAmount(chitFundMember.getWinningAmount())
                   .winningMonth(chitFundMember.getWinningMonth())
                   .monthsPaid(chitFundMember.getMonthsPaid())
                   .totalPaid(chitFundMember.getTotalPaid())
                   .build();
           members.add(member);
       }


        List<MonthlyRecordDto> monthlyRecords = new ArrayList<>();
        for(MonthlyRecord monthlyRecord : retreviedChitFund.getMonthlyRecords()){
            MonthlyRecordDto record = MonthlyRecordDto.builder()
                    .id(monthlyRecord.getId())
                    .monthNumber(monthlyRecord.getMonthNumber())
                    .status(monthlyRecord.getStatus())
                    .auctionDateTime(monthlyRecord.getAuctionDateTime())
                    .totalCollectedAmount(monthlyRecord.getTotalCollectedAmount())
                    .amountSaved(monthlyRecord.getAmountSaved())
                    .isCompleted(monthlyRecord.getIsCompleted())
                    .build();
            monthlyRecords.add(record);
        }


        return ChitFundResponse.builder()
                .id(retreviedChitFund.getId())
                .name(retreviedChitFund.getName())
                .totalAmount(retreviedChitFund.getTotalAmount())
                .totalMonths(retreviedChitFund.getTotalMonths())
                .currentMonth(retreviedChitFund.getCurrentMonth())
                .cutOffAmount(retreviedChitFund.getCutOffAmount())
                .interestRate(retreviedChitFund.getInterestRate())
                .savedPool(retreviedChitFund.getSavedPool())
                .status(retreviedChitFund.getStatus())
                .createdAt(retreviedChitFund.getCreatedAt())
                .owner(retreviedChitFund.getOwner().getName())
                .coOwner(retreviedChitFund.getCoOwner().getName())
                .members(members)
                .monthlyRecords(monthlyRecords)
                .build();
    }

    public ResponseMessageDto deleteChitFund(Integer chitFundId){
        chitFundRepository.findById(chitFundId)
                .orElseThrow(()-> new ResourceNotFound("Chit fund not found"));
        chitFundRepository.deleteById(chitFundId);
        return ResponseMessageDto.builder()
                .message("Chit fund deleted successfully")
                .build();
    }

}
