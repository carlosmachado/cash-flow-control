package br.com.cmachado.cashflowcontrol.infrastructure.timezone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimezoneConfig {

    @Value("${timezone}")
    private String timeZone;

    @PostConstruct
    public void setGlobalConfiguration(){
        setGlobalTimeZone();
    }

    private void setGlobalTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }
}
