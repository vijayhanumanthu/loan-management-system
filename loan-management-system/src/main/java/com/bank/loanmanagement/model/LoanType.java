package com.bank.loanmanagement.model;

public enum LoanType {
    MORTGAGE_30_YEAR("30-Year Fixed Mortgage", 6.85),
    MORTGAGE_15_YEAR("15-Year Fixed Mortgage", 6.10),
    AUTO_LOAN("Auto Loan", 7.20),
    PERSONAL_LOAN("Personal Loan", 12.50),
    BUSINESS_LOAN("Business Loan", 9.50),
    STUDENT_LOAN("Student Loan", 5.50),
    HOME_EQUITY("Home Equity Loan", 8.75);
    
    private final String displayName;
    private final Double currentBaseRate; // Base rate as of Feb 2026
    
    LoanType(String displayName, Double currentBaseRate) {
        this.displayName = displayName;
        this.currentBaseRate = currentBaseRate;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public Double getCurrentBaseRate() {
        return currentBaseRate;
    }
}
