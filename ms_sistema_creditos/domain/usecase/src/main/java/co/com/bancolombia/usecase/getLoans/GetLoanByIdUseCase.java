package co.com.bancolombia.usecase.getLoans;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.exceptions.BusinessException;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLoanByIdUseCase {

    private final LoanRepository loanRepository;

    public Loan execute(Long id) {

        return loanRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Loan not found"));
    }
}
