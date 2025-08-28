package com.itai.itaiJavaPlay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

  @GetMapping("/")
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

  // @GetMapping("/users")
  // public String users(Model model) {
  // model.addAttribute("pageTitle", "User Management");
  // model.addAttribute("template", "fragments/users");
  // return "layout"; // Return the main layout template
  // }

  @GetMapping("/test")
  public String testHarness(Model model) {
    model.addAttribute("pageTitle", "Test Harness");
    model.addAttribute("template", "test-harness");
    return "layout";
  }
}