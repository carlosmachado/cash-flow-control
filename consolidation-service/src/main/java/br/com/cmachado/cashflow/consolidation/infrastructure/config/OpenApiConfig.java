package br.com.cmachado.cashflow.consolidation.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI consolidationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Consolidation Service API")
                        .description("Consolidado diário — balance and daily transaction reports")
                        .version("v1"));
    }
}
