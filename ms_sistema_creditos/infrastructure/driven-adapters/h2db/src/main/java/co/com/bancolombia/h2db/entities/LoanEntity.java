package co.com.bancolombia.h2db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private Integer age;
    private Integer creditScore;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyDebt;
    private BigDecimal requestedAmount;
    private Boolean approved;
    private String status;
    private BigDecimal interestRate;
    private String rejectionReason;

}