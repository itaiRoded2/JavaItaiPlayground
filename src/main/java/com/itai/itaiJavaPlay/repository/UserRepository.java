package com.itai.itaiJavaPlay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itai.itaiJavaPlay.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find user by email (case-insensitive)
   */
  Optional<User> findByEmail(String email);

  /**
   * Find users by name containing text (case-insensitive)
   */
  List<User> findByNameContainingIgnoreCase(String name);

  /**
   * Find all active users
   */
  List<User> findByIsActiveTrue();

  /**
   * Find all inactive users
   */
  List<User> findByIsActiveFalse();

  /**
   * Find active users with pagination
   */
  Page<User> findByIsActiveTrue(Pageable pageable);

  /**
   * Count active users
   */
  long countByIsActiveTrue();

  /**
   * Count inactive users
   */
  long countByIsActiveFalse();

  /**
   * Find users created after a specific date
   */
  List<User> findByCreatedAtAfter(LocalDateTime date);

  /**
   * Find users created between two dates
   */
  List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  /**
   * Find users by email domain
   */
  @Query("SELECT u FROM User u WHERE u.email LIKE %:domain%")
  List<User> findByEmailDomain(@Param("domain") String domain);

  /**
   * Find users with bio containing text
   */
  List<User> findByBioContainingIgnoreCase(String bioText);

  /**
   * Search users by name or email
   */
  @Query("SELECT u FROM User u WHERE " +
      "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
      "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
  List<User> searchByNameOrEmail(@Param("searchTerm") String searchTerm);

  /**
   * Find recent users (created in last N days)
   */
  @Query("SELECT u FROM User u WHERE u.createdAt >= :date ORDER BY u.createdAt DESC")
  List<User> findRecentUsers(@Param("date") LocalDateTime date);

  /**
   * Find users with empty or null bio
   */
  @Query("SELECT u FROM User u WHERE u.bio IS NULL OR TRIM(u.bio) = ''")
  List<User> findUsersWithoutBio();

  /**
   * Get user statistics
   */
  @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
  long getActiveUserCount();

  @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
  long getUsersCreatedSince(@Param("date") LocalDateTime date);

  /**
   * Find users ordered by creation date (newest first)
   */
  List<User> findAllByOrderByCreatedAtDesc();

  /**
   * Find users ordered by name
   */
  List<User> findAllByOrderByNameAsc();

  /**
   * Check if email exists (case-insensitive)
   */
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE LOWER(u.email) = LOWER(:email)")
  boolean existsByEmailIgnoreCase(@Param("email") String email);

  /**
   * Delete users by active status
   */
  void deleteByIsActiveFalse();

  /**
   * Update user active status
   */
  @Query("UPDATE User u SET u.isActive = :active, u.updatedAt = :updatedAt WHERE u.id = :id")
  int updateUserActiveStatus(@Param("id") Long id, @Param("active") boolean active,
      @Param("updatedAt") LocalDateTime updatedAt);
}