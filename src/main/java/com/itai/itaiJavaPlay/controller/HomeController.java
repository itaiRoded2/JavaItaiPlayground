package com.itai.itaiJavaPlay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

@Controller
@Tag(name = "Home Controller", description = "Home application endpoints")
public class HomeController {

  @GetMapping("/")
  @Operation(summary = "Home Page", description = "Returns the main application page")
  public String home(Model model) {
    model.addAttribute("pageTitle", "Home");
    model.addAttribute("currentTime",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    // Get environment from system property or environment variable
    String environment = System.getProperty("spring.profiles.active");
    if (environment == null || environment.isEmpty()) {
      environment = System.getenv("SPRING_PROFILES_ACTIVE");
    }
    if (environment == null || environment.isEmpty()) {
      // Check Railway's built-in environment variable
      environment = System.getenv("RAILWAY_ENVIRONMENT");
    }
    if (environment == null || environment.isEmpty()) {
      environment = "local"; // default fallback
    }

    model.addAttribute("environment", environment.toUpperCase());

    // Specify the fragment name
    model.addAttribute("template", "home");
    // model.addAttribute("template", "home");
    // Return the main layout template
    return "layout";
  }

  @GetMapping("/test")
  public String testHarness(Model model) {
    model.addAttribute("pageTitle", "Test Harness");
    model.addAttribute("template", "test-harness");
    return "layout";
  }

  // API endpoints for Swagger documentation (using different paths to avoid
  // conflicts)
  @GetMapping("/api/home/status")
  @ResponseBody
  @Operation(summary = "Home Status Check", description = "Returns the current status from the home controller perspective", responses = {
      @ApiResponse(responseCode = "200", description = "Home API is healthy"),
      @ApiResponse(responseCode = "500", description = "Home API is unhealthy")
  })
  public ResponseEntity<Map<String, Object>> homeStatus() {
    Map<String, Object> status = new HashMap<>();
    status.put("status", "healthy");
    status.put("timestamp", LocalDateTime.now().toString());
    status.put("application", "Itai Java Playground");
    status.put("version", "1.0.0");
    status.put("controller", "HomeController");

    String environment = System.getProperty("spring.profiles.active");
    if (environment == null || environment.isEmpty()) {
      environment = System.getenv("SPRING_PROFILES_ACTIVE");
    }
    if (environment == null || environment.isEmpty()) {
      environment = System.getenv("RAILWAY_ENVIRONMENT");
    }
    if (environment == null || environment.isEmpty()) {
      environment = "local";
    }

    status.put("environment", environment);
    status.put("uptime", "Server is running");
    status.put("swagger_docs", "/swagger-ui/index.html");

    return ResponseEntity.ok(status);
  }

  @GetMapping("/api/app/info")
  @ResponseBody
  @Operation(summary = "Application Info", description = "Returns basic application information")
  public ResponseEntity<Map<String, String>> appInfo() {
    Map<String, String> info = new HashMap<>();
    info.put("name", "Itai Java Playground");
    info.put("description", "Spring Boot application deployed on Railway");
    info.put("version", "1.0.0");
    info.put("docs", "/swagger-ui/index.html");
    info.put("production_url", "https://itaijavaplay-production.up.railway.app/");
    info.put("controller", "HomeController");

    return ResponseEntity.ok(info);
  }

  @GetMapping("/api/app/health")
  @ResponseBody
  @Operation(summary = "App Health Check", description = "Simple health check endpoint from home controller")
  public ResponseEntity<Map<String, String>> appHealth() {
    Map<String, String> health = new HashMap<>();
    health.put("status", "UP");
    health.put("timestamp", LocalDateTime.now().toString());
    health.put("controller", "HomeController");

    return ResponseEntity.ok(health);
  }
}