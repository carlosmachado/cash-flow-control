package br.com.cmachado.cashflow.consolidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "br.com.cmachado.cashflow.consolidation",
        "br.com.cmachado.cashflow.shared"
})
public class ConsolidationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsolidationServiceApplication.class, args);
    }
}
