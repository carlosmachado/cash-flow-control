package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.dailytransaction;

import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.ConsolidatedAmount;
import br.com.cmachado.cashflowcontrol.domain.model.dailytransaction.DailyTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailyTransactions")
public class DailyTransactionRestController {
    private final ModelMapper modelMapper;
    private final DailyTransactionRepository dailyTransactionRepository;

    public DailyTransactionRestController(ModelMapper modelMapper,
                                          DailyTransactionRepository dailyTransactionRepository) {
        this.modelMapper = modelMapper;
        this.dailyTransactionRepository = dailyTransactionRepository;
    }

    @GetMapping("{date}")
    public ResponseEntity<DailyTransactionsByDateResponse> getByDate(@PathVariable String date) {

        var localDate = LocalDate.parse(date);

        var dailyTransactions = dailyTransactionRepository.findAllByDate(localDate);

        if (dailyTransactions.isEmpty())
            return ResponseEntity.ok(DailyTransactionsByDateResponse.empty());

        var consolidatedAmount = ConsolidatedAmount.ofTransactions(dailyTransactions.get());

        List<DailyTransactionDTO> dtos = dailyTransactions.get()
                .stream()
                .map(tra -> modelMapper.map(tra, DailyTransactionDTO.class))
                .collect(Collectors.toList());

        var response = DailyTransactionsByDateResponse.builder()
                .balances(dtos)
                .count(dtos.size())
                .consolidatedAmount(consolidatedAmount.getValue().getValue())
                .build();

        return ResponseEntity.ok(response);
    }
}
