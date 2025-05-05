package com.example.buyornot.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI buyOrNotOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BuyOrNot API")
                        .description("구매 결정을 도와주는 API 문서")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("개발자 이름")
                                .email("email@example.com")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("깃허브 레포지토리")
                        .url("https://github.com/your-repo")
                );
    }
}
