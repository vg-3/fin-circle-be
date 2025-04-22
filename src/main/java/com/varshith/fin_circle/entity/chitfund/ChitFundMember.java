package com.varshith.fin_circle.entity.chitfund;

import com.varshith.fin_circle.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chit_fund_members")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChitFundMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean hasWon;
    private double winningAmount;
    private int winningMonth;
    private int monthsPaid;
    private double totalPaid;
    private Integer guarantorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chit_fund_id")
    private ChitFund chitFund;



    @OneToMany(mappedBy = "chitFundMember")
    private List<WinningBid> winningBids;
}

