package co.com.bancolombia.model.loan;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {
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