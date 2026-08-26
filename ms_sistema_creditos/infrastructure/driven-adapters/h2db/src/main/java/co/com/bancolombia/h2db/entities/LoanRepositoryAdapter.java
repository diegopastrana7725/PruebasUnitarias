package co.com.bancolombia.h2db.entities;

import co.com.bancolombia.h2db.utilities.LoanMapper;
import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanRepositoryAdapter
        implements LoanRepository {

    private final LoanDataRepository repository;
    private final LoanMapper mapper;

    @Override
    public Loan save(Loan loan) {
        LoanEntity entity = mapper.toEntity(loan);
        return mapper.toModel(repository.save(entity));
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return repository.findById(id)
            .map(mapper::toModel);
    }

    @Override
    public List<Loan> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toModel)
            .toList();
    }
}