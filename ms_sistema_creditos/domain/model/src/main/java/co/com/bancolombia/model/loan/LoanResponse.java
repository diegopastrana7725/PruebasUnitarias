package co.com.bancolombia.model.loan;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanResponse {
    private Long id;
    private Boolean approved;
    private String status;
    private BigDecimal interestRate;
    private String rejectionReason;
}