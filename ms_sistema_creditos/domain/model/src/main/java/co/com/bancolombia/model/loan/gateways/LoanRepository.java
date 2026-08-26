package co.com.bancolombia.model.loan.gateways;

import co.com.bancolombia.model.loan.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findAll();
}
