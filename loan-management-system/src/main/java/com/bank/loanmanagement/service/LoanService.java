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

    }
