package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.balance;

import br.com.cmachado.cashflowcontrol.domain.model.balance.Balance;
import br.com.cmachado.cashflowcontrol.domain.model.balance.BalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balances")
public class BalanceRestController {
    private final ModelMapper modelMapper;
    private final BalanceRepository balanceRepository;

    public BalanceRestController(ModelMapper modelMapper,
                                 BalanceRepository balanceRepository) {
        this.modelMapper = modelMapper;
        this.balanceRepository = balanceRepository;
    }

    @GetMapping
    public ResponseEntity<BalanceDTO> getDefault() {

        var balances = balanceRepository.findAll();

        Balance balance;

        if (balances.isEmpty())
            balance = Balance.start();
        else
            balance = balances.get(0);

        var dto = modelMapper.map(balance, BalanceDTO.class);

        return ResponseEntity.ok(dto);
    }
}
