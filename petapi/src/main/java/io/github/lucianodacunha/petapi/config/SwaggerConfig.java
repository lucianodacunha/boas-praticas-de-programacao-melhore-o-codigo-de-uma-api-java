package io.github.lucianodacunha.petapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("oluciano.dev@gmail.com");
        contact.setName("Luciano");
        contact.setUrl("http://localhost:8080/swagger-ui.html");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Boas práticas de programação: Melhore o código de uma API Java")
                .version("1.0")
                .contact(contact)
                .license(mitLicense);

        return new OpenAPI().info(info);
    }

}
