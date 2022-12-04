package br.com.cmachado.cashflowcontrol.application.credit;

import br.com.cmachado.cashflowcontrol.domain.model.common.money.Money;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionDate;
import br.com.cmachado.cashflowcontrol.domain.model.common.transaction.TransactionId;
import br.com.cmachado.cashflowcontrol.domain.model.credit.Credit;
import br.com.cmachado.cashflowcontrol.domain.model.credit.CreditNotFoundException;
import br.com.cmachado.cashflowcontrol.domain.model.credit.CreditRepository;
import br.com.cmachado.cashflowcontrol.domain.shared.ApplicationService;
import jakarta.transaction.Transactional;

@ApplicationService
public class CreditService {
    private CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Transactional
    public Credit execute(RegisterCreditRequest request){
        var transactionId = TransactionId.generate();
        var transactionDate = TransactionDate.was(request.getTransactionDate());
        var type = request.getType();
        var amount = Money.of(request.getAmount());
        var description = request.getDescription();

        var credit = Credit.register(
                transactionId,
                transactionDate,
                type,
                amount,
                description
        );

        creditRepository.save(credit);

        return credit;
    }

    @Transactional
    public Credit deleteCredit(TransactionId transactionId){
        var credit = creditRepository.findById(transactionId)
                .orElseThrow(() -> new CreditNotFoundException(transactionId));

        credit.delete();

        creditRepository.save(credit);

        return credit;
    }
}
