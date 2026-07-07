package com.bank.loanmanagement.repository;

import com.bank.loanmanagement.model.Loan;
import com.bank.loanmanagement.model.LoanStatus;
import com.bank.loanmanagement.model.LoanType;
import com.bank.loanmanagement.model.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByStatus(LoanStatus status);
	List<Loan> findByLoanType(LoanType loanType);
    List<Loan> findByCustomerEmail(String email);
    List<Loan> findByRiskLevel(RiskLevel riskLevel);
    List<Loan> findByApplicationDateBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT l FROM Loan l WHERE l.status IN ('ACTIVE', 'DISBURSED')")
    List<Loan> findActiveLoans();
    @Query("SELECT SUM(l.loanAmount) FROM Loan l WHERE l.status IN ('ACTIVE', 'DISBURSED')")
    Double getTotalActiveLoansAmount();
    @Query("SELECT l FROM Loan l WHERE l.creditScore < 650 AND l.status = 'PENDING'")
    List<Loan> findHighRiskPendingLoans();


}
