package com.delivery.trizi.trizi.infra.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Ecommerce",
        description = "Api completa para Ecommerce",
        version = "V1",
        contact = @Contact(
                name = "Yuri Matthewus",
                email = "yuri_matteus@hotmail.com",
                url = "https://github.com/iru-Y/Ecommerce-DropShipping-Java")
))
public class OpenApiInfra {
    @Bean
    public GroupedOpenApi logApi() {
        log.info("Gerando swagger da aplicação");
        return GroupedOpenApi
                .builder()
                .group("api-v1")
                .packagesToScan("com.delivery.trizi.trizi.controller")
                .pathsToMatch("/v1/**")
                .build();
    }
}