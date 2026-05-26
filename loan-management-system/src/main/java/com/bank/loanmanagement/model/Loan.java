package com.bank.loanmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String customerEmail;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanType loanType;
    
    @Column(nullable = false)
    private Double loanAmount;
    
    @Column(nullable = false)
    private Integer termMonths;
    
    @Column(nullable = false)
    private Double interestRate; // Annual percentage rate
    
    @Column(nullable = false)
    private Double monthlyPayment;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;
    
    @Column(nullable = false)
    private LocalDateTime applicationDate;
    
    private LocalDateTime approvalDate;
    
    private LocalDateTime disbursementDate;
    
    @Column(nullable = false)
    private Integer creditScore;
    
    private String employmentStatus;
    
    private Double annualIncome;
    
    @Column(length = 1000)
    private String notes;
    
    // Risk assessment fields
    private Double debtToIncomeRatio;
    
    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;
    
    // Current market scenario tracking
    private String marketCondition; // e.g., "Rising rates Q1 2026"
    
    private Double currentFedRate; // Track Fed rate at application time
}
