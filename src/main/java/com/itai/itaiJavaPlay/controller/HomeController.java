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

    // Use direct HTML content instead of fragments
    model.addAttribute("content", """
        <div class="feature">
            <h3>Welcome to Itai Java playground (Home Controller)</h3>
            <p>Current time: %s</p>
            <p>The application is running successfully! TEST3</p>

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
        """.formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

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