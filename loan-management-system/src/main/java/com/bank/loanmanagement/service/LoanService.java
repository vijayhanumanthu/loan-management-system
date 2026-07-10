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
    public Loan createLoan(Loan loan) {
        loan.setApplicationDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.PENDING);
        loan.setCurrentFedRate(CURRENT_FED_RATE);
        loan.setMarketCondition(CURRENT_MARKET_CONDITION);
}
