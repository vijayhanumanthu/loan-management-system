package com.bank.loanmanagement.model;

public enum RiskLevel {
    LOW("Low Risk - Excellent Profile"),
    MEDIUM("Medium Risk - Good Profile"),
    HIGH("High Risk - Caution Advised"),
    VERY_HIGH("Very High Risk - Review Required");
    
    private final String description;
    
    RiskLevel(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
