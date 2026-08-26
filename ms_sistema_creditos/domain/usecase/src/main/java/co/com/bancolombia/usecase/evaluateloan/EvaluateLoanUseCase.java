package co.com.bancolombia.usecase.evaluateloan;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.LoanRequest;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import co.com.bancolombia.model.loan.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class EvaluateLoanUseCase {

    private static final int MIN_AGE = 18;
    private static final int MIN_SCORE = 600;

    private final LoanRepository loanRepository;

    public Loan execute(Loan loan) {

        try {

            validateAge(loan);
            validateCreditScore(loan);
            validateDebtRatio(loan);
            validateMaximumAmount(loan);

            BigDecimal interestRate =
                    calculateInterestRate(loan.getCreditScore());

            loan.setApproved(true);
            loan.setStatus("APPROVED");
            loan.setInterestRate(interestRate);
            loan.setRejectionReason(null);

        } catch (BusinessException e) {

            loan.setApproved(false);
            loan.setStatus("REJECTED");
            loan.setInterestRate(null);
            loan.setRejectionReason(e.getMessage());
        }

        return loanRepository.save(loan);
    }

    private void validateAge(Loan loan) {

        if (loan.getAge() < MIN_AGE) {
            throw new BusinessException(
                "Customer must be at least 18 years old");
        }
    }

    private void validateCreditScore(Loan loan) {

        if (loan.getCreditScore() < MIN_SCORE) {
            throw new BusinessException(
                "Credit score must be at least 600");
        }
    }

    private void validateDebtRatio(Loan loan) {

        BigDecimal debtRatio =
            loan.getMonthlyDebt()
                .divide(
                    loan.getMonthlyIncome(),
                    2,
                    RoundingMode.HALF_UP
                );

        if (debtRatio.compareTo(BigDecimal.valueOf(0.40)) > 0) {
            throw new BusinessException("Debt ratio exceeds 40%");
        }
    }

    private void validateMaximumAmount(Loan loan) {

        BigDecimal maxAllowed =
            loan.getMonthlyIncome().multiply(BigDecimal.TEN);

        if (loan.getRequestedAmount().compareTo(maxAllowed) > 0) {
            throw new BusinessException(
                "Requested amount exceeds allowed limit");
        }
    }

    private BigDecimal calculateInterestRate(Integer score) {

        if (score >= 800) {
            return BigDecimal.valueOf(8);
        }

        if (score >= 700) {
            return BigDecimal.valueOf(10);
        }

        return BigDecimal.valueOf(12);
    }
}
