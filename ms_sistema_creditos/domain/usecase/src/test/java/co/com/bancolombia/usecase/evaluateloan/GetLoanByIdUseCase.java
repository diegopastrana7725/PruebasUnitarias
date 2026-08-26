package co.com.bancolombia.usecase.evaluateloan;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.exceptions.BusinessException;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import co.com.bancolombia.usecase.getLoans.GetLoanByIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLoanByIdUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private GetLoanByIdUseCase useCase;

    @Test
    void shouldReturnLoanWhenLoanExists() {

        Long loanId = 1L;

        Loan loan = new Loan();
        loan.setId(loanId);

        when(loanRepository.findById(loanId))
                .thenReturn(Optional.of(loan));

        Loan result = useCase.execute(loanId);

        assertNotNull(result);
        assertEquals(loanId, result.getId());

        verify(loanRepository, times(1))
                .findById(loanId);
    }

    @Test
    void shouldThrowBusinessExceptionWhenLoanDoesNotExist() {

        Long loanId = 999L;

        when(loanRepository.findById(loanId))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> useCase.execute(loanId)
        );

        assertEquals(
                "Loan not found",
                exception.getMessage()
        );

        verify(loanRepository, times(1))
                .findById(loanId);
    }
}
