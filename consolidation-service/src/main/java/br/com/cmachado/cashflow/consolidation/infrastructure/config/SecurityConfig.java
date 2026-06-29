package br.com.cmachado.cashflow.consolidation.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security for service consumption (integration).
 *
 * Default: open, so local curl / docker-compose work out of the box.
 * With {@code app.security.jwt.enabled=true} the API becomes a JWT (OAuth2)
 * resource server — set {@code spring.security.oauth2.resourceserver.jwt.issuer-uri}.
 * Actuator and OpenAPI endpoints stay open for scraping/docs.
 */
@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC = {
            "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Bean
    @ConditionalOnProperty(name = "app.security.jwt.enabled", havingValue = "true")
    public SecurityFilterChain jwtSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                }));
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "app.security.jwt.enabled", havingValue = "false", matchIfMissing = true)
    public SecurityFilterChain openSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
