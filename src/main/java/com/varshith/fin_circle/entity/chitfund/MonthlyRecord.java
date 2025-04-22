package com.varshith.fin_circle.entity.chitfund;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.enumeration.MONTHLY_RECORD_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "monthly_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int monthNumber;

    @Enumerated(EnumType.STRING)
    private MONTHLY_RECORD_STATUS status;

    private Instant auctionDateTime;
    private float totalCollectedAmount;
    private float amountSaved;
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "chit_fund_id", nullable = false)
    private ChitFund chitFund;

    @OneToMany(mappedBy = "monthlyRecord", cascade = CascadeType.ALL)
    private List<WinningBid> winningBids;

    @OneToMany(mappedBy = "monthlyRecord",cascade = CascadeType.ALL)
    private List<MonthlyPayment> payments;
}
