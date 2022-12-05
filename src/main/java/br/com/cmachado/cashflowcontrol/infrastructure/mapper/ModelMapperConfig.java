package br.com.cmachado.cashflowcontrol.infrastructure.mapper;

import br.com.cmachado.cashflowcontrol.domain.model.transaction.Transaction;
import br.com.cmachado.cashflowcontrol.presentation.controllers.rest.transaction.TransactionDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Transaction.class, TransactionDTO.class);

        modelMapper.addConverter(optionalConverter());

        modelMapper.validate();

        return modelMapper;
    }

    private Converter<Optional<Object>, Object> optionalConverter() {
        return ctx -> {

            if (ctx.getSource().isEmpty()) {
                return null;
            }

            MappingContext<Object, Object> propertyContext = ctx.create(
                    ctx.getSource().get(),
                    ctx.getDestinationType());

            return ctx.getMappingEngine().map(propertyContext);
        };
    }
}


