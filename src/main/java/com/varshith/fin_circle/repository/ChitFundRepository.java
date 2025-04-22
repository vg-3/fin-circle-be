package com.varshith.fin_circle.repository;

import com.varshith.fin_circle.entity.chitfund.ChitFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitFundRepository extends JpaRepository<ChitFund, Integer> {

    List<ChitFund> findByOwnerId(Integer ownerId);

    List<ChitFund> findByCoOwnerId(Integer ownerId);

    @Query("SELECT DISTINCT c FROM ChitFund c JOIN c.members m WHERE m.user.id = :userId")
    List<ChitFund> findByMemberUserId(@Param("userId") Integer userId);

}
