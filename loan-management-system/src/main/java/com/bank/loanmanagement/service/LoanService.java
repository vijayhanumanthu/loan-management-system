package com.bank.loanmanagement.service;

import com.bank.loanmanagement.model.*;
import com.bank.loanmanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    
    // Current Fed rate as of Feb 2026 (estimated based on market conditions)
    private static final Double CURRENT_FED_RATE = 4.50;
    private static final String CURRENT_MARKET_CONDITION = "Moderately High Rates - Q1 2026";

    public Loan createLoan(Loan loan) {
        loan.setApplicationDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.PENDING);
        loan.setCurrentFedRate(CURRENT_FED_RATE);
        loan.setMarketCondition(CURRENT_MARKET_CONDITION);
        
        
        // Calculate interest rate based on loan type and credit score
        Double baseRate = loan.getLoanType().getCurrentBaseRate();
        Double adjustedRate = calculateAdjustedRate(baseRate, loan.getCreditScore());
        loan.setInterestRate(adjustedRate);
        
     // Calculate monthly payment
        Double monthlyPayment = calculateMonthlyPayment(
            loan.getLoanAmount(), 
            adjustedRate, 
            loan.getTermMonths()
        );
        loan.setMonthlyPayment(monthlyPayment);
        
     // Calculate debt-to-income ratio if income is provided
        if (loan.getAnnualIncome() != null && loan.getAnnualIncome() > 0) {
            Double monthlyIncome = loan.getAnnualIncome() / 12;
            loan.setDebtToIncomeRatio((monthlyPayment / monthlyIncome) * 100);
        }
        
        // Assess risk level
        loan.setRiskLevel(assessRiskLevel(loan));
        
        return loanRepository.save(loan);
    }
    private Double calculateAdjustedRate(Double baseRate, Integer creditScore) {
        // Adjust rate based on credit score
        if (creditScore >= 750) {
            return baseRate - 0.50; // Excellent credit discount
        } else if (creditScore >= 700) {
            return baseRate - 0.25; // Good credit discount
        } else if (creditScore >= 650) {
            return baseRate; // Fair credit - base rate
        } else if (creditScore >= 600) {
            return baseRate + 0.75; // Poor credit surcharge
        } else {
            return baseRate + 1.50; // Very poor credit surcharge
        }
    }
    private Double calculateMonthlyPayment(Double principal, Double annualRate, Integer months) {
        Double monthlyRate = annualRate / 100 / 12;
        if (monthlyRate == 0) {
            return principal / months;
        }
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months)) / 
               (Math.pow(1 + monthlyRate, months) - 1);
    }
    private RiskLevel assessRiskLevel(Loan loan) {
        int riskScore = 0;
        
        // Credit score assessment
        if (loan.getCreditScore() < 600) riskScore += 3;
        else if (loan.getCreditScore() < 650) riskScore += 2;
        else if (loan.getCreditScore() < 700) riskScore += 1;
        // Debt-to-income ratio assessment
        if (loan.getDebtToIncomeRatio() != null) {
            if (loan.getDebtToIncomeRatio() > 43) riskScore += 3;
            else if (loan.getDebtToIncomeRatio() > 36) riskScore += 2;
            else if (loan.getDebtToIncomeRatio() > 28) riskScore += 1;
        }
        // Loan amount to income ratio
        if (loan.getAnnualIncome() != null && loan.getAnnualIncome() > 0) {
            Double loanToIncomeRatio = loan.getLoanAmount() / loan.getAnnualIncome();
            if (loanToIncomeRatio > 4) riskScore += 2;
            else if (loanToIncomeRatio > 3) riskScore += 1;
        }
        // Determine risk level
        if (riskScore >= 6) return RiskLevel.VERY_HIGH;
        if (riskScore >= 4) return RiskLevel.HIGH;
        if (riskScore >= 2) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
    public Loan approveLoan(Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setStatus(LoanStatus.APPROVED);
            loan.setApprovalDate(LocalDateTime.now());
            return loanRepository.save(loan);
        }
        throw new RuntimeException("Loan not found with id: " + loanId);
    }
    
    
    public Loan rejectLoan(Long loanId, String reason) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setStatus(LoanStatus.REJECTED);
            loan.setNotes(reason);
            return loanRepository.save(loan);
        }
        throw new RuntimeException("Loan not found with id: " + loanId);
    }
    
        
    }

    

