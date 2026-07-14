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
    }
}
