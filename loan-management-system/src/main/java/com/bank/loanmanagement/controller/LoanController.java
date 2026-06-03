package com.bank.loanmanagement.controller;

import com.bank.loanmanagement.model.Loan;
import com.bank.loanmanagement.model.LoanStatus;
import com.bank.loanmanagement.model.LoanType;
import com.bank.loanmanagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        try {
            Loan createdLoan = loanService.createLoan(loan);
            return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
