package com.varshith.fin_circle.entity.chitfund;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.varshith.fin_circle.entity.User;
import jakarta.persistence.*;
import lombok.*;

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
    private LocalDate auctionDate;
    private float totalCollectedAmount;
    private float amountSaved;
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "chit_fund_id", nullable = false)
    private ChitFund chitFund;

    @OneToMany(mappedBy = "monthlyRecord", cascade = CascadeType.ALL)
    private List<WinningBid> winningBids;
}
