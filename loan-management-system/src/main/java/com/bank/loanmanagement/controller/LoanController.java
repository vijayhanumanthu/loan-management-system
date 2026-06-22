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
    
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.getLoanById(id);
        return loan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable String status) {
        try {
            LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());
            List<Loan> loans = loanService.getLoansByStatus(loanStatus);
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable String type) {
        try {
            LoanType loanType = LoanType.valueOf(type.toUpperCase());
            List<Loan> loans = loanService.getLoansByType(loanType);
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> loans = loanService.getActiveLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @GetMapping("/high-risk")
    public ResponseEntity<List<Loan>> getHighRiskPendingLoans() {
        List<Loan> loans = loanService.getHighRiskPendingLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    @GetMapping("/total-active-amount")
    public ResponseEntity<Double> getTotalActiveLoansAmount() {
        Double total = loanService.getTotalActiveLoansAmount();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<Loan> approveLoan(@PathVariable Long id) {
        try {
            Loan approvedLoan = loanService.approveLoan(id);
            return new ResponseEntity<>(approvedLoan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<Loan> rejectLoan(@PathVariable Long id, @RequestBody String reason) {
        try {
            Loan rejectedLoan = loanService.rejectLoan(id, reason);
            return new ResponseEntity<>(rejectedLoan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}/disburse")
    public ResponseEntity<Loan> disburseLoan(@PathVariable Long id) {
        try {
            Loan disbursedLoan = loanService.disburseLoan(id);
            return new ResponseEntity<>(disbursedLoan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        try {
            Loan updatedLoan = loanService.updateLoan(id, loan);
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
