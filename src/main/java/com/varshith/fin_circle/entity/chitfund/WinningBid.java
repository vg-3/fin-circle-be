package com.varshith.fin_circle.entity.chitfund;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "winning_bids")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinningBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "monthly_record_id")
    private MonthlyRecord monthlyRecord;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private ChitFundMember chitFundMember;

    private float winningAmount;
    private float savedAmount;
}