package com.itai.itaiJavaPlay.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

  @Value("${server.port:8080}")
  private String serverPort;

  @Bean
  public OpenAPI customOpenAPI() {
    // Create servers for both local and production
    Server localServer = new Server()
        .url("http://localhost:" + serverPort)
        .description("Local Development Server");

    Server productionServer = new Server()
        .url("https://itaijavaplay-production.up.railway.app")
        .description("Production Server (Railway)");

    return new OpenAPI()
        .info(new Info()
            .title("Itai Java Playground API")
            .version("1.0.0")
            .description("Spring Boot application deployed on Railway with comprehensive API documentation")
            .contact(new Contact()
                .name("Itai")
                .url("https://itaijavaplay-production.up.railway.app/")))
        .servers(List.of(localServer, productionServer));
  }
}