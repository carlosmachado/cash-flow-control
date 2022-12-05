package br.com.cmachado.cashflowcontrol.utils;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLContainerUtils {
    public static PostgreSQLContainer<?> startContainer() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("tst")
                .withUsername("postgres")
                .withPassword("postgres")
                .withExposedPorts(5432);

        postgres.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/tst", postgres.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "postgres");
        System.setProperty("spring.datasource.password", "postgres");

        return postgres;
    }
}
