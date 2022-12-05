package br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction;

import br.com.cmachado.cashflowcontrol.application.transaction.RegisterTransactionRequest;
import br.com.cmachado.cashflowcontrol.application.transaction.TransactionService;
import br.com.cmachado.cashflowcontrol.domain.model.transaction.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionRestController(ModelMapper modelMapper,
                                     TransactionService transactionService,
                                     TransactionRepository transactionRepository) {
        this.modelMapper = modelMapper;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> register(@RequestBody @Valid RegisterTransactionRequest request) {

        var transaction = transactionService.execute(request);

        var dto = modelMapper.map(transaction, TransactionDTO.class);

        var uri = URI.create("/transactions/" + transaction.getId());

        return ResponseEntity.created(uri)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAll() {

        var transactions = transactionRepository.findAll();

        List<TransactionDTO> dtos = transactions
                .stream()
                .map(tra -> modelMapper.map(tra, TransactionDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
