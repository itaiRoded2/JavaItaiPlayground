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

    model.addAttribute("content",
        """
            <div class="feature">
                <h3>Welcome to Spring Railway App</h3>
                <p><strong>Environment: %s</strong></p>
                <p>Current time: %s</p>
                <p>The application is running successfully!</p>
                <p><strong>Online URL:</strong> <a href="https://itaijavaplay-production.up.railway.app/" target="_blank" class="btn btn-info" style="display: inline-flex; align-items: center; gap: 8px; padding: 8px 12px; margin-left: 8px;">
                    <i class="fas fa-external-link-alt"></i>
                    Visit Production
                </a></p>

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
            """
            .formatted(environment.toUpperCase(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    return "layout";
  }

  // @GetMapping("/users")
  // public String users(Model model) {
  // model.addAttribute("pageTitle", "User Management");
  // model.addAttribute("template", "users");
  // return "layout";
  // }

  @GetMapping("/users")
  public String users(Model model) {
    model.addAttribute("pageTitle", "User Management");

    model.addAttribute("content", """
        <div class="feature">
            <h3>User Management</h3>
            <p>Manage application users and their permissions.</p>

            <div class="feature">
                <h3>User Actions</h3>
                <a href="/users/create" class="btn btn-success">
                    <i class="fas fa-user-plus"></i>
                    Add New User
                </a>
                <a href="/users/list" class="btn btn-primary">
                    <i class="fas fa-list"></i>
                    View All Users
                </a>
                <a href="/users/search" class="btn btn-info">
                    <i class="fas fa-search"></i>
                    Search Users
                </a>
            </div>

            <div class="feature">
                <h3>Recent Users</h3>
                <p>No users found. <a href="/users/create">Create your first user</a></p>
            </div>
        </div>
        """);

    return "layout"; // Still returns layout to keep your header/nav/styling
  }

  @GetMapping("/test")
  public String testHarness(Model model) {
    model.addAttribute("pageTitle", "Test Harness");
    model.addAttribute("template", "test-harness");
    return "layout";
  }
}