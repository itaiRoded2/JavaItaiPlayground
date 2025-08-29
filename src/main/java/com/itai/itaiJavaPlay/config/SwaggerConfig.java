package com.itai.itaiJavaPlay.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

  @Value("${server.port:8080}")
  private String serverPort;

  @Bean
  public OpenAPI customOpenAPI() {
    List<Server> servers = new ArrayList<>();

    // Local server
    servers.add(new Server()
        .url("http://localhost:" + serverPort)
        .description("Local Development Server"));

    // Railway server (existing)
    servers.add(new Server()
        .url("https://itaijavaplay-production.up.railway.app")
        .description("Production Server (Railway)"));

    // Render server (new) - Update this with your actual Render URL
    servers.add(new Server()
        .url("https://your-render-app-name.onrender.com")
        .description("Production Server (Render)"));

    return new OpenAPI()
        .info(new Info()
            .title("Itai Java Playground API")
            .version("1.0.0")
            .description("Spring Boot application deployed on Railway and Render with comprehensive API documentation")
            .contact(new Contact()
                .name("Itai")
                .url("https://itaijavaplay-production.up.railway.app/")))
        .servers(servers);
  }
}