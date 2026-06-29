package br.com.cmachado.cashflow.consolidation.infrastructure.config;

import br.com.cmachado.cashflow.consolidation.domain.model.balance.Balance;
import br.com.cmachado.cashflow.consolidation.domain.model.dailytransaction.DailyTransaction;
import br.com.cmachado.cashflow.consolidation.presentation.balance.BalanceDTO;
import br.com.cmachado.cashflow.consolidation.presentation.dailytransaction.DailyTransactionDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Balance.class, BalanceDTO.class);

        modelMapper.createTypeMap(DailyTransaction.class, DailyTransactionDTO.class);

        modelMapper.validate();

        return modelMapper;
    }
}
