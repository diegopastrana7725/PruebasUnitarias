package co.com.bancolombia.usecase.evaluateloan;

import co.com.bancolombia.model.loan.Loan;
import co.com.bancolombia.model.loan.gateways.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluateLoanUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private EvaluateLoanUseCase useCase;

    @BeforeEach
    void setUp() {
        when(loanRepository.save(any(Loan.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldApproveLoanWith8PercentInterestRate() {

        Loan loan = buildLoan(
                35,
                850,
                8_000_000,
                2_000_000,
                30_000_000
        );

        Loan result = useCase.execute(loan);

        assertTrue(result.getApproved());
        assertEquals("APPROVED", result.getStatus());
        assertEquals(BigDecimal.valueOf(8), result.getInterestRate());
        assertNull(result.getRejectionReason());

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void shouldApproveLoanWith10PercentInterestRate() {

        Loan loan = buildLoan(
                30,
                750,
                6_000_000,
                1_000_000,
                20_000_000
        );

        Loan result = useCase.execute(loan);

        assertTrue(result.getApproved());
        assertEquals("APPROVED", result.getStatus());
        assertEquals(BigDecimal.valueOf(10), result.getInterestRate());
        assertNull(result.getRejectionReason());
    }

    @Test
    void shouldApproveLoanWith12PercentInterestRate() {

        Loan loan = buildLoan(
                25,
                650,
                5_000_000,
                1_000_000,
                10_000_000
        );

        Loan result = useCase.execute(loan);

        assertTrue(result.getApproved());
        assertEquals("APPROVED", result.getStatus());
        assertEquals(BigDecimal.valueOf(12), result.getInterestRate());
        assertNull(result.getRejectionReason());
    }

    @Test
    void shouldRejectLoanWhenCustomerIsUnderAge() {

        Loan loan = buildLoan(
                17,
                800,
                5_000_000,
                1_000_000,
                10_000_000
        );

        Loan result = useCase.execute(loan);

        assertFalse(result.getApproved());
        assertEquals("REJECTED", result.getStatus());
        assertNull(result.getInterestRate());

        assertEquals(
                "Customer must be at least 18 years old",
                result.getRejectionReason()
        );
    }

    @Test
    void shouldRejectLoanWhenCreditScoreIsLowerThan600() {

        Loan loan = buildLoan(
                30,
                550,
                5_000_000,
                1_000_000,
                10_000_000
        );

        Loan result = useCase.execute(loan);

        assertFalse(result.getApproved());

        assertEquals(
                "Credit score must be at least 600",
                result.getRejectionReason()
        );
    }

    @Test
    void shouldRejectLoanWhenDebtRatioExceeds40Percent() {

        Loan loan = buildLoan(
                30,
                700,
                5_000_000,
                2_500_000,
                10_000_000
        );

        Loan result = useCase.execute(loan);

        assertFalse(result.getApproved());

        assertEquals(
                "Debt ratio exceeds 40%",
                result.getRejectionReason()
        );
    }

    @Test
    void shouldRejectLoanWhenRequestedAmountExceedsLimit() {

        Loan loan = buildLoan(
                30,
                700,
                5_000_000,
                1_000_000,
                60_000_000
        );

        Loan result = useCase.execute(loan);

        assertFalse(result.getApproved());

        assertEquals(
                "Requested amount exceeds allowed limit",
                result.getRejectionReason()
        );
    }

    @Test
    void shouldSaveApprovedLoan() {

        Loan loan = buildLoan(
                35,
                850,
                8_000_000,
                2_000_000,
                30_000_000
        );

        useCase.execute(loan);

        verify(loanRepository, times(1))
                .save(any(Loan.class));
    }

    @Test
    void shouldSaveRejectedLoan() {

        Loan loan = buildLoan(
                17,
                850,
                8_000_000,
                2_000_000,
                30_000_000
        );

        useCase.execute(loan);

        verify(loanRepository, times(1))
                .save(any(Loan.class));
    }

    @Test
    void shouldPersistRejectedReason() {

        Loan loan = buildLoan(
                17,
                850,
                8_000_000,
                2_000_000,
                30_000_000
        );

        ArgumentCaptor<Loan> captor =
                ArgumentCaptor.forClass(Loan.class);

        useCase.execute(loan);

        verify(loanRepository).save(captor.capture());

        Loan savedLoan = captor.getValue();

        assertEquals(
                "Customer must be at least 18 years old",
                savedLoan.getRejectionReason()
        );

        assertEquals(
                "REJECTED",
                savedLoan.getStatus()
        );
    }

    private Loan buildLoan(
            int age,
            int score,
            long income,
            long debt,
            long amount) {

        Loan loan = new Loan();

        loan.setCustomerId("10001");
        loan.setAge(age);
        loan.setCreditScore(score);
        loan.setMonthlyIncome(BigDecimal.valueOf(income));
        loan.setMonthlyDebt(BigDecimal.valueOf(debt));
        loan.setRequestedAmount(BigDecimal.valueOf(amount));

        return loan;
    }
}