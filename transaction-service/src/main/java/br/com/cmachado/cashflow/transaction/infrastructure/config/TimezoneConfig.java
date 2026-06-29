package br.com.cmachado.cashflow.transaction.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimezoneConfig {

    @Value("${timezone}")
    private String timeZone;

    @PostConstruct
    public void setGlobalConfiguration() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }
}
