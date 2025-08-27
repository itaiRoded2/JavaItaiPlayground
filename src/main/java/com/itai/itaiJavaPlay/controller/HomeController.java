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
    model.addAttribute("template", "index"); // This tells layout.html which template to load
    model.addAttribute("currentTime",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    model.addAttribute("contentTemplate", "fragments/home :: content");

    return "layout"; // Always Returns layout.html
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