package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.dailybalance;

import br.com.cmachado.cashflowcontrol.domain.model.dailybalance.DailyBalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailyBalances")
public class DailyBalanceRestController {
    private final ModelMapper modelMapper;
    private final DailyBalanceRepository dailyBalanceRepository;

    public DailyBalanceRestController(ModelMapper modelMapper,
                                      DailyBalanceRepository dailyBalanceRepository) {
        this.modelMapper = modelMapper;
        this.dailyBalanceRepository = dailyBalanceRepository;
    }

    @GetMapping("{date}")
    public ResponseEntity<DailyBalancesByDateResponse> getByDate(@PathVariable String date) {

        var localDate = LocalDate.parse(date);

        var balances = dailyBalanceRepository.findAllByDate(localDate);

        if (balances.isEmpty())
            return ResponseEntity.ok(DailyBalancesByDateResponse.empty());

        List<DailyBalanceDTO> dtos = balances.get()
                .stream()
                .map(bal -> modelMapper.map(bal, DailyBalanceDTO.class))
                .collect(Collectors.toList());

        var response = DailyBalancesByDateResponse.builder()
                .balances(dtos)
                .count(dtos.size())
                .build();

        return ResponseEntity.ok(response);
    }
}
