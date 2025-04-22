package com.varshith.fin_circle.service;

import com.varshith.fin_circle.dto.AddressDto;
import com.varshith.fin_circle.dto.chitfund.*;
import com.varshith.fin_circle.dto.chitfund.request.UpdateGuarantorRequest;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundMemberResponse;
import com.varshith.fin_circle.dto.chitfund.response.ChitFundResponse;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.entity.chitfund.ChitFund;
import com.varshith.fin_circle.entity.chitfund.ChitFundMember;
import com.varshith.fin_circle.exception.InvalidOperationException;
import com.varshith.fin_circle.exception.ResourceNotFound;
import com.varshith.fin_circle.repository.ChitFundMemberRepository;
import com.varshith.fin_circle.repository.ChitFundRepository;
import com.varshith.fin_circle.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChitFundMemberService {

    private final ChitFundRepository chitFundRepository;

    private final ChitFundMemberRepository chitFundMemberRepository;

    private final UserRepository userRepository;

    public ChitFundResponse addMember(Integer chitFundId,
                                      List<Integer> userIds){
//      check if chit fund is null
        if(chitFundId == null){
            throw new IllegalArgumentException("Chit fund id cannot be null");
        }

//        check if user to be added are null
        if(userIds == null){
            throw new  IllegalArgumentException("users cannot be null");
        }

//        get existing chitFund from db
        ChitFund retrievedChitFund = chitFundRepository.findById(chitFundId).orElseThrow(()->
                new ResourceNotFound("chit fund not found"));

//        extract existing members from chit fund
        List<ChitFundMember> chitFundMembers = retrievedChitFund.getMembers();

//        map through userIds and add those users to chitFund and update the guarantor later
        for(Integer userId : userIds){
            User userToBeAdded = userRepository.findById(userId)
                    .orElseThrow(()-> new ResourceNotFound("Member not found"));
            ChitFundMember member = ChitFundMember.builder()
                    .hasWon(false)
                    .monthsPaid(0)
                    .totalPaid(0)
                    .user(userToBeAdded)
                    .chitFund(retrievedChitFund)
                    .build();
            chitFundMembers.add(member);
        }
        retrievedChitFund.setMembers(chitFundMembers);
//        save updated chitFund
        ChitFund savedChitFund = chitFundRepository.save(retrievedChitFund);

//        Building response
        List<ChitFundMemberDto> members = new ArrayList<>();

        for(ChitFundMember chitFundMember : savedChitFund.getMembers()){
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

        return ChitFundResponse.builder()
                .id(savedChitFund.getId())
                .name(savedChitFund.getName())
                .totalAmount(savedChitFund.getTotalAmount())
                .totalMonths(savedChitFund.getTotalMonths())
                .currentMonth(savedChitFund.getCurrentMonth())
                .cutOffAmount(savedChitFund.getCutOffAmount())
                .interestRate(savedChitFund.getInterestRate())
                .savedPool(savedChitFund.getSavedPool())
                .status(savedChitFund.getStatus())
                .createdAt(savedChitFund.getCreatedAt())
                .owner(savedChitFund.getOwner().getName())
                .coOwner(savedChitFund.getCoOwner().getName())
                .members(members)
                .build();
    }

    @Transactional
    public ChitFundResponse removeMember(Integer chitFundId,
                                                  Set<Integer> usersToRemove) {
        if(chitFundId == null){
            throw new IllegalArgumentException("Chit fund id cannot be null");
        }

        if (usersToRemove == null || usersToRemove.isEmpty()) {
            throw new IllegalArgumentException("Member list cannot be null or empty");
        }

        ChitFund retrievedChitFund = chitFundRepository.findById(chitFundId).orElseThrow(()->
                new ResourceNotFound("chit fund not found"));

        if (retrievedChitFund.getMembers() == null || retrievedChitFund.getMembers().isEmpty()) {
            throw new InvalidOperationException("This chit fund has no members to remove");
        }


        List<ChitFundMember> retrievedMembers = retrievedChitFund.getMembers();

       List<ChitFundMember> memberStillInChitFund = retrievedMembers.stream().filter(
               chitFundMember -> !usersToRemove.contains(chitFundMember.getId()))
               .toList();

       List<ChitFundMember> guarantorsBeingRemoved = memberStillInChitFund.stream().filter(
               member -> member.getGuarantorId() != null
       ).filter(member -> usersToRemove.contains(member.getGuarantorId())).toList();

        if(!guarantorsBeingRemoved.isEmpty()){
            throw new InvalidOperationException("""
                    Cannot remove users who are guarantors for others still in the fund.""");

        }

//        also check if all usersToRemove exists in chitFund
        Set<Integer> retrievedUserIds = retrievedMembers.stream()
                .map(ChitFundMember::getId).collect(Collectors.toSet());
        boolean allExists = retrievedUserIds.containsAll(usersToRemove);

        if(!allExists){
            throw new InvalidOperationException("Members be to be removed are not part of chit fund.");
        }

        retrievedChitFund.getMembers().removeIf(member ->
                usersToRemove.contains(member.getId()));

        ChitFund savedChitFund = chitFundRepository.save(retrievedChitFund);

//      Building Response
        List<ChitFundMemberDto> members = new ArrayList<>();

        for(ChitFundMember chitFundMember : savedChitFund.getMembers()){
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

        return ChitFundResponse.builder()
                .id(savedChitFund.getId())
                .name(savedChitFund.getName())
                .totalAmount(savedChitFund.getTotalAmount())
                .totalMonths(savedChitFund.getTotalMonths())
                .currentMonth(savedChitFund.getCurrentMonth())
                .cutOffAmount(savedChitFund.getCutOffAmount())
                .interestRate(savedChitFund.getInterestRate())
                .savedPool(savedChitFund.getSavedPool())
                .status(savedChitFund.getStatus())
                .createdAt(savedChitFund.getCreatedAt())
                .owner(savedChitFund.getOwner().getName())
                .coOwner(savedChitFund.getCoOwner().getName())
                .members(members)
                .build();
    }

//  update guarantor for member
    public ChitFundMemberResponse updateGuarantor(UpdateGuarantorRequest updateGuarantorRequest){
        Integer memberId = updateGuarantorRequest.memberId();
        Integer guarantorMemberId = updateGuarantorRequest.guarantorMemberId();

        ChitFundMember retrievedMember = chitFundMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFound("chit fund member not found"));

        retrievedMember.setGuarantorId(guarantorMemberId);

        ChitFundMember updatedMember = chitFundMemberRepository.save(retrievedMember);

//        Building Response
//        get guarantor details
        ChitFundMember retrievedGuarantor = chitFundMemberRepository.findById(guarantorMemberId)
                .orElseThrow(() -> new ResourceNotFound("Guarantor not found"));
        GuarantorDto guarantor = GuarantorDto.builder()
                .name(retrievedGuarantor.getUser().getName())
                .imageUrl(retrievedGuarantor.getUser().getUserDetails().getImageUrl())
                .build();
//        user address
        AddressDto address = AddressDto.builder()
                .apartmentName(updatedMember.getUser().getUserDetails().getAddress().getApartmentName())
                .flatNumber(updatedMember.getUser().getUserDetails().getAddress().getFlatNumber())
                .street(updatedMember.getUser().getUserDetails().getAddress().getStreet())
                .landmark(updatedMember.getUser().getUserDetails().getAddress().getLandmark())
                .city(updatedMember.getUser().getUserDetails().getAddress().getCity())
                .state(updatedMember.getUser().getUserDetails().getAddress().getState())
                .pinCode(updatedMember.getUser().getUserDetails().getAddress().getPinCode())
                .build();

        return ChitFundMemberResponse.builder()
                .userId(updatedMember.getUser().getId())
                .name(updatedMember.getUser().getName())
                .email(updatedMember.getUser().getEmail())
                .phoneNumber(updatedMember.getUser().getPhoneNumber())
                .dob(updatedMember.getUser().getUserDetails().getDob())
                .imageUrl(updatedMember.getUser().getUserDetails().getImageUrl())
                .aadhaarNumber(updatedMember.getUser().getUserDetails().getAadhaarNumber())
                .panNumber(updatedMember.getUser().getUserDetails().getPanNumber())
                .kycDoc(updatedMember.getUser().getUserDetails().getKycDoc())
                .hasWon(updatedMember.isHasWon())
                .winningAmount(updatedMember.getWinningAmount())
                .winningMonth(updatedMember.getWinningMonth())
                .monthsPaid(updatedMember.getMonthsPaid())
                .totalPaid(updatedMember.getTotalPaid())
                .guarantor(guarantor)
                .address(address)
                .build();
    }

}

