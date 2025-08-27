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
      environment = "local"; // default fallback
    }

    model.addAttribute("content", """
        <div class="feature">
            <h3>Welcome to Spring Railway App</h3>
            <p><strong>Environment: %s</strong></p>
            <p>Current time: %s</p>
            <p>The application is running successfully!</p>

            <div class="feature">
                <h3>Quick Actions</h3>
                <a href="/users" class="btn btn-primary">
                    <i class="fas fa-users"></i>
                    Manage Users
                </a>
                <a href="/test" class="btn btn-success">
                    <i class="fas fa-flask"></i>
                    Run Tests
                </a>
            </div>
        </div>
        """.formatted(environment.toUpperCase(),
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    return "layout";
  }

  @GetMapping("/users")
  public String users(Model model) {
    model.addAttribute("pageTitle", "User Management");
    model.addAttribute("template", "users");
    return "layout";
  }

  @GetMapping("/test")
  public String testHarness(Model model) {
    model.addAttribute("pageTitle", "Test Harness");
    model.addAttribute("template", "test-harness");
    return "layout";
  }
}