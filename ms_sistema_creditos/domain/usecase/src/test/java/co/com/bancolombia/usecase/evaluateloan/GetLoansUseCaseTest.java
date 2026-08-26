package co.com.bancolombia.usecase.evaluateloan;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import co.com.bancolombia.usecase.getLoans.GetLoansUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLoansUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private GetLoansUseCase useCase;

    @Test
    void shouldReturnAllLoans() {

        Loan loan1 = new Loan();
        loan1.setId(1L);

        Loan loan2 = new Loan();
        loan2.setId(2L);

        List<Loan> expectedLoans = List.of(loan1, loan2);

        when(loanRepository.findAll())
                .thenReturn(expectedLoans);

        List<Loan> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals(expectedLoans, result);

        verify(loanRepository, times(1))
                .findAll();
    }
}
