package br.com.cmachado.cashflowcontrol.infrastructure.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SpringFoxConfig {
    @Bean
    public Docket api() {

        var docket = new Docket(DocumentationType.SWAGGER_2);

        return docket.select()
                .apis(RequestHandlerSelectors.basePackage("br.com.cmachado.cashflowcontrol.presentation.controllers.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}
