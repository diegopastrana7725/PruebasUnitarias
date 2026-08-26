package co.com.bancolombia.usecase.getLoans;


import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetLoansUseCase {

    private final LoanRepository loanRepository;

    public List<Loan> execute() {
        return loanRepository.findAll();
    }
}
