package com.bank.loanmanagement.model;

public enum LoanStatus {
    PENDING("Application Pending Review"),
    UNDER_REVIEW("Under Review"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    DISBURSED("Funds Disbursed"),
    ACTIVE("Active - In Repayment"),
    PAID_OFF("Paid Off"),
    DEFAULTED("Defaulted");
    
    private final String description;
    
    LoanStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
