package com.itai.itaiJavaPlay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itai.itaiJavaPlay.model.User;
import com.itai.itaiJavaPlay.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users
   */
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Get all active users
   */
  @Transactional(readOnly = true)
  public List<User> getAllActiveUsers() {
    return userRepository.findByIsActiveTrue();
  }

  /**
   * Get users with pagination
   */
  @Transactional(readOnly = true)
  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  /**
   * Get user by ID
   */
  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }

  /**
   * Get user by email
   */
  @Transactional(readOnly = true)
  public User getUserByEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.orElse(null);
  }

  /**
   * Search users by name (case-insensitive)
   */
  @Transactional(readOnly = true)
  public List<User> searchUsersByName(String namePattern) {
    return userRepository.findByNameContainingIgnoreCase(namePattern);
  }

  /**
   * Save or update user
   */
  public User saveUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    // Check if email is already taken (for new users or email changes)
    if (user.getId() == null || !user.getEmail().equals(getUserById(user.getId()).getEmail())) {
      User existingUser = getUserByEmail(user.getEmail());
      if (existingUser != null && !existingUser.getId().equals(user.getId())) {
        throw new IllegalArgumentException("Email is already taken: " + user.getEmail());
      }
    }

    // Set update time for existing users
    if (user.getId() != null) {
      user.setUpdatedAt(LocalDateTime.now());
    }

    return userRepository.save(user);
  }

  /**
   * Create new user
   */
  public User createUser(String name, String email) {
    if (getUserByEmail(email) != null) {
      throw new IllegalArgumentException("User with email " + email + " already exists");
    }

    User user = new User(name, email);
    return userRepository.save(user);
  }

  /**
   * Create new user with bio
   */
  public User createUser(String name, String email, String bio) {
    if (getUserByEmail(email) != null) {
      throw new IllegalArgumentException("User with email " + email + " already exists");
    }

    User user = new User(name, email, bio);
    return userRepository.save(user);
  }

  /**
   * Update user
   */
  public User updateUser(Long id, String name, String email, String bio) {
    User user = getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found with id: " + id);
    }

    // Check if new email is already taken by another user
    if (!user.getEmail().equals(email)) {
      User existingUser = getUserByEmail(email);
      if (existingUser != null && !existingUser.getId().equals(id)) {
        throw new IllegalArgumentException("Email is already taken: " + email);
      }
    }

    user.setName(name);
    user.setEmail(email);
    user.setBio(bio);
    user.setUpdatedAt(LocalDateTime.now());

    return userRepository.save(user);
  }

  /**
   * Deactivate user (soft delete)
   */
  public void deactivateUser(Long id) {
    User user = getUserById(id);
    if (user != null) {
      user.setIsActive(false);
      user.setUpdatedAt(LocalDateTime.now());
      userRepository.save(user);
    }
  }

  /**
   * Activate user
   */
  public void activateUser(Long id) {
    User user = getUserById(id);
    if (user != null) {
      user.setIsActive(true);
      user.setUpdatedAt(LocalDateTime.now());
      userRepository.save(user);
    }
  }

  /**
   * Delete user permanently
   */
  public void deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("User not found with id: " + id);
    }
  }

  /**
   * Check if user exists by ID
   */
  @Transactional(readOnly = true)
  public boolean userExists(Long id) {
    return userRepository.existsById(id);
  }

  /**
   * Check if user exists by email
   */
  @Transactional(readOnly = true)
  public boolean userExistsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  /**
   * Get total user count
   */
  @Transactional(readOnly = true)
  public long getTotalUserCount() {
    return userRepository.count();
  }

  /**
   * Get active user count
   */
  @Transactional(readOnly = true)
  public long getActiveUserCount() {
    return userRepository.countByIsActiveTrue();
  }

  /**
   * Create sample users for testing
   */
  public void createSampleUsers() {
    if (userRepository.count() == 0) {
      List<User> sampleUsers = List.of(
          new User("John Doe", "john@example.com", "Software Developer with 5+ years experience"),
          new User("Jane Smith", "jane@example.com", "Product Manager specializing in agile methodologies"),
          new User("Bob Johnson", "bob@example.com", "DevOps Engineer focusing on cloud infrastructure"),
          new User("Alice Williams", "alice@example.com", "UX Designer passionate about user-centered design"),
          new User("Charlie Brown", "charlie@example.com", "Data Scientist working with machine learning"));

      userRepository.saveAll(sampleUsers);
    }
  }
}