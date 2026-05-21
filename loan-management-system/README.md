# Bank Loan Management System

A comprehensive Spring Boot application for processing and tracking bank loans with current 2026 market scenarios.

## Features

- **Loan Application Processing**: Create and manage loan applications
- **Current Market Rates**: Uses real-world 2026 interest rates
  - 30-Year Mortgage: 6.85%
  - 15-Year Mortgage: 6.10%
  - Auto Loans: 7.20%
  - Personal Loans: 12.50%
  - Business Loans: 9.50%
- **Risk Assessment**: Automatic credit score and debt-to-income ratio analysis
- **Loan Tracking**: Track loans through their entire lifecycle
- **RESTful API**: Complete REST API for all operations

## Technology Stack

- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- H2 Database (development)
- Lombok
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Installation & Setup

1. **Clone or extract the project**

2. **Build the project**
   ```bash
   cd loan-management-system
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api/loans`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:loandb`
     - Username: `sa`
     - Password: (leave empty)

## API Endpoints

### Loan Operations

#### Create a new loan application
```http
POST /api/loans
Content-Type: application/json

{
  "customerName": "John Doe",
  "customerEmail": "john.doe@email.com",
  "phoneNumber": "+1-555-0123",
  "loanType": "MORTGAGE_30_YEAR",
  "loanAmount": 350000.00,
  "termMonths": 360,
  "creditScore": 720,
  "employmentStatus": "Full-time",
  "annualIncome": 85000.00
}
```

#### Get all loans
```http
GET /api/loans
```

#### Get loan by ID
```http
GET /api/loans/{id}
```

#### Get loans by status
```http
GET /api/loans/status/{status}
```
Status values: `PENDING`, `UNDER_REVIEW`, `APPROVED`, `REJECTED`, `DISBURSED`, `ACTIVE`, `PAID_OFF`, `DEFAULTED`

#### Get loans by type
```http
GET /api/loans/type/{type}
```
Type values: `MORTGAGE_30_YEAR`, `MORTGAGE_15_YEAR`, `AUTO_LOAN`, `PERSONAL_LOAN`, `BUSINESS_LOAN`, `STUDENT_LOAN`, `HOME_EQUITY`

#### Get active loans
```http
GET /api/loans/active
```

#### Get high-risk pending loans
```http
GET /api/loans/high-risk
```

#### Get total active loans amount
```http
GET /api/loans/total-active-amount
```

#### Approve a loan
```http
PUT /api/loans/{id}/approve
```

#### Reject a loan
```http
PUT /api/loans/{id}/reject
Content-Type: application/json

"Insufficient credit score"
```

#### Disburse a loan
```http
PUT /api/loans/{id}/disburse
```

#### Update loan details
```http
PUT /api/loans/{id}
Content-Type: application/json

{
  "customerName": "John Doe Updated",
  "customerEmail": "john.updated@email.com",
  "phoneNumber": "+1-555-0124",
  "notes": "Updated contact information"
}
```

#### Delete a loan
```http
DELETE /api/loans/{id}
```

## Loan Types & Current Rates (Feb 2026)

| Loan Type | Base Rate | Description |
|-----------|-----------|-------------|
| MORTGAGE_30_YEAR | 6.85% | 30-Year Fixed Mortgage |
| MORTGAGE_15_YEAR | 6.10% | 15-Year Fixed Mortgage |
| AUTO_LOAN | 7.20% | Auto Loan |
| PERSONAL_LOAN | 12.50% | Personal Loan |
| BUSINESS_LOAN | 9.50% | Business Loan |
| STUDENT_LOAN | 5.50% | Student Loan |
| HOME_EQUITY | 8.75% | Home Equity Loan |

## Risk Assessment

The system automatically calculates risk levels based on:

1. **Credit Score**
   - < 600: +3 risk points
   - 600-649: +2 risk points
   - 650-699: +1 risk point
   - 700+: 0 risk points

2. **Debt-to-Income Ratio**
   - > 43%: +3 risk points
   - 36-43%: +2 risk points
   - 28-36%: +1 risk point
   - < 28%: 0 risk points

3. **Loan-to-Income Ratio**
   - > 4x: +2 risk points
   - 3-4x: +1 risk point
   - < 3x: 0 risk points

**Risk Levels:**
- LOW: 0-1 points
- MEDIUM: 2-3 points
- HIGH: 4-5 points
- VERY_HIGH: 6+ points

## Interest Rate Adjustments

Base rates are adjusted based on credit score:
- 750+: -0.50% (Excellent)
- 700-749: -0.25% (Good)
- 650-699: Base rate (Fair)
- 600-649: +0.75% (Poor)
- < 600: +1.50% (Very Poor)

## Market Conditions Tracking

Each loan captures:
- Current Federal Reserve rate (4.50% as of Feb 2026)
- Market condition description
- Application timestamp

## Production Deployment

For production use:

1. **Update application.properties** to use PostgreSQL or MySQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/loandb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=validate
   ```

2. **Uncomment the database dependency** in pom.xml

3. **Add security** (Spring Security) for authentication and authorization

4. **Configure SSL/TLS** for secure communications

5. **Set up proper logging** and monitoring

## Testing

Run tests with:
```bash
mvn test
```

## Project Structure

```
loan-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/bank/loanmanagement/
│   │   │   ├── LoanManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   └── LoanController.java
│   │   │   ├── model/
│   │   │   │   ├── Loan.java
│   │   │   │   ├── LoanType.java
│   │   │   │   ├── LoanStatus.java
│   │   │   │   └── RiskLevel.java
│   │   │   ├── repository/
│   │   │   │   └── LoanRepository.java
│   │   │   └── service/
│   │   │       └── LoanService.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Future Enhancements

- Payment tracking and amortization schedules
- Email notifications for loan status changes
- Document upload (income verification, ID, etc.)
- Credit bureau API integration
- Advanced analytics and reporting dashboard
- Multi-currency support
- Loan refinancing options
- Mobile app integration

## License

This project is for educational and demonstration purposes.

## Support

For issues or questions, please create an issue in the project repository.
