package com.itai.itaiJavaPlay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller to handle all user-related requests, including listing and
 * creating users.
 * This class demonstrates how to manage different views for a single logical
 * area
 * of the application.
 */
@Controller
@RequestMapping("/users")
public class UserController {

  // In a real application, this would be a service or a repository.
  private List<User> mockUsers = new ArrayList<>();

  // Constructor to pre-populate some mock data
  public UserController() {
    mockUsers.add(new User("john.doe@example.com", "John Doe"));
    mockUsers.add(new User("jane.smith@example.com", "Jane Smith"));
  }

  /**
   * Handles the GET request for the user list page.
   * It sets the active navigation item and the page title.
   * * @param model The Spring Model to pass data to the view.
   * 
   * @return The name of the layout template to render.
   */
  @GetMapping
  public String listUsers(Model model) {
    model.addAttribute("pageTitle", "User Management");
    model.addAttribute("requestURI", "/users"); // Used by the layout to set active-nav
    model.addAttribute("template", "users/list");
    model.addAttribute("users", mockUsers); // Pass the list of users to the view
    return "layout";
  }

  /**
   * Handles the GET request for the user creation form.
   * It adds an empty User object to the model for form binding.
   * * @param model The Spring Model to pass data to the view.
   * 
   * @return The name of the layout template to render.
   */
  @GetMapping("/create")
  public String showCreateUserForm(Model model) {
    model.addAttribute("pageTitle", "Create User");
    model.addAttribute("requestURI", "/users/create"); // Set active-nav
    model.addAttribute("template", "users/create");
    model.addAttribute("user", new User()); // Add a new, empty User object for the form
    return "layout";
  }

  /**
   * Handles the POST request for user creation.
   * It binds the form data to the User object.
   * * @param user The User object populated by the form data.
   * 
   * @return A redirect to the user list page.
   */
  @PostMapping("/create")
  public String createUser(@ModelAttribute User user) {
    // In a real application, you would save the user to a database here.
    mockUsers.add(user);
    // You can add logic here to handle validation and errors.
    return "redirect:/users";
  }

}

/**
 * A simple model class to represent a user.
 * This class would typically be in a separate file, like
 * src/main/java/com/itai/java/playground/model/User.java
 */
class User {
  private String email;
  private String name;

  public User() {
  }

  public User(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
