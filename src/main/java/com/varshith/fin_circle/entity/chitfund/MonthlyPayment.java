package com.varshith.fin_circle.entity.chitfund;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "monthly_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_record_id")
    private MonthlyRecord monthlyRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private ChitFundMember chitFundMember;

    private double paidAmount;

    private Instant paymentDateTime;

    private boolean isLate;

    private double fineAmount;
}
