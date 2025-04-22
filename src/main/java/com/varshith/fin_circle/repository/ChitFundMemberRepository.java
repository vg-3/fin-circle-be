package com.varshith.fin_circle.repository;

import com.varshith.fin_circle.entity.chitfund.ChitFundMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChitFundMemberRepository extends JpaRepository<ChitFundMember, Integer> {
}
