package br.com.cmachado.cashflowcontrol.utils;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer<?> postgres;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres = startContainer();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        //postgres.stop();
    }

    public static PostgreSQLContainer<?> startContainer() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("cash_flow_test")
                .withUsername("postgres")
                .withPassword("docker")
                .withExposedPorts(5432);

        postgres.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/cash_flow_test", postgres.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "postgres");
        System.setProperty("spring.datasource.password", "docker");

        return postgres;
    }
}
