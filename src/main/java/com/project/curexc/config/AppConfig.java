package com.project.curexc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EntityScan(basePackages = "com.project.curexc.model")
@EnableJpaRepositories(basePackages = "com.project.curexc.repository")
public class AppConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Exchange API")
                        .version("1.0")
                        .description("API for managing currency exchange rates"));
    }
}
