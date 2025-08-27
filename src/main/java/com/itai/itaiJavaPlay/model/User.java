package com.itai.itaiJavaPlay.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  @Column(nullable = false, length = 100)
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Please provide a valid email address")
  @Column(nullable = false, unique = true, length = 255)
  private String email;

  @Size(max = 500, message = "Bio cannot exceed 500 characters")
  @Column(length = 500)
  private String bio;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "is_active")
  private Boolean isActive = true;

  // Default constructor
  public User() {
  }

  // Constructor with name and email
  public User(String name, String email) {
    this.name = name;
    this.email = email;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor with all fields
  public User(String name, String email, String bio) {
    this.name = name;
    this.email = email;
    this.bio = bio;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // JPA lifecycle callbacks
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    if (this.isActive == null) {
      this.isActive = true;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  // Utility methods
  public String getDisplayName() {
    return name != null ? name : "Unknown User";
  }

  public String getShortBio() {
    if (bio == null || bio.trim().isEmpty()) {
      return "No bio provided";
    }
    return bio.length() > 100 ? bio.substring(0, 97) + "..." : bio;
  }

  // toString, equals, and hashCode
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", isActive=" + isActive +
        ", createdAt=" + createdAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return id != null && id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}