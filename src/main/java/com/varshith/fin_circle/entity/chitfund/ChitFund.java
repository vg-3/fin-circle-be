package com.varshith.fin_circle.entity.chitfund;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.enumeration.ChitFundStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "chit_funds")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChitFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double totalAmount;
    private double monthlyPayable;
    private int totalMonths;
    private int currentMonth;
    private double cutOffAmount;
    private double interestRate;
    private double savedPool;
    private ChitFundStatus status;
    private Instant createdAt;
//    "2025-04-12T10:30:00Z"


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "co_owner_id")
    private User coOwner;

    @OneToMany(mappedBy = "chitFund", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChitFundMember> members;

    @OneToMany(mappedBy = "chitFund", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlyRecord> monthlyRecords;
}


// chit fund should have one owner
// one user can be owner of multiple chit funds