package co.com.bancolombia.h2db.utilities;

import co.com.bancolombia.h2db.entities.LoanEntity;
import co.com.bancolombia.model.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanEntity toEntity(Loan loan) {

        LoanEntity entity = new LoanEntity();

        entity.setId(loan.getId());
        entity.setCustomerId(loan.getCustomerId());
        entity.setAge(loan.getAge());
        entity.setCreditScore(loan.getCreditScore());
        entity.setMonthlyIncome(loan.getMonthlyIncome());
        entity.setMonthlyDebt(loan.getMonthlyDebt());
        entity.setRequestedAmount(loan.getRequestedAmount());
        entity.setApproved(loan.getApproved());
        entity.setStatus(loan.getStatus());
        entity.setInterestRate(loan.getInterestRate());
        entity.setRejectionReason(loan.getRejectionReason());

        return entity;
    }

    public Loan toModel(LoanEntity entity) {

        Loan loan = new Loan();

        loan.setId(entity.getId());
        loan.setCustomerId(entity.getCustomerId());
        loan.setAge(entity.getAge());
        loan.setCreditScore(entity.getCreditScore());
        loan.setMonthlyIncome(entity.getMonthlyIncome());
        loan.setMonthlyDebt(entity.getMonthlyDebt());
        loan.setRequestedAmount(entity.getRequestedAmount());
        loan.setApproved(entity.getApproved());
        loan.setStatus(entity.getStatus());
        loan.setInterestRate(entity.getInterestRate());
        loan.setRejectionReason(entity.getRejectionReason());

        return loan;
    }
}