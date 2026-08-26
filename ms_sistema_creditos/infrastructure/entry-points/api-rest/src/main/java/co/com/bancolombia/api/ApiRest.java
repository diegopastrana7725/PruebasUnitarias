package co.com.bancolombia.api;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.LoanRequest;
import co.com.bancolombia.model.loan.LoanResponse;
import co.com.bancolombia.model.loan.exceptions.BusinessException;
import co.com.bancolombia.usecase.evaluateloan.EvaluateLoanUseCase;
import co.com.bancolombia.usecase.getLoans.GetLoanByIdUseCase;
import co.com.bancolombia.usecase.getLoans.GetLoansUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ApiRest {

    private final GetLoansUseCase getLoansUseCase;
    private final GetLoanByIdUseCase getLoanByIdUseCase;
    private final EvaluateLoanUseCase evaluateLoanUseCase;

    @PostMapping("/loans")
    public LoanResponse createLoan(@RequestBody LoanRequest request) {

        Loan loan = new Loan();

        loan.setCustomerId(request.getCustomerId());
        loan.setAge(request.getAge());
        loan.setCreditScore(request.getCreditScore());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setMonthlyDebt(request.getMonthlyDebt());
        loan.setRequestedAmount(request.getRequestedAmount());

        Loan loanResp=evaluateLoanUseCase.execute(loan);
        return LoanResponse.builder()
            .id(loanResp.getId())
            .interestRate(loanResp.getInterestRate())
            .rejectionReason(loanResp.getRejectionReason())
            .status(loanResp.getStatus())
            .approved(loanResp.getApproved())
            .build();
    }

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getLoans() {

        List<Loan> loans = getLoansUseCase.execute();

        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable("id") Long id) {

        try {
            Loan loan = getLoanByIdUseCase.execute(id);

            return ResponseEntity.ok(loan);

        } catch (BusinessException e) {
            return ResponseEntity.noContent().build();
        }
    }
}