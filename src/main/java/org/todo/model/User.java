package org.todo.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID id;
    private String firstname;
    private String lastname;
    private final String email;
    private String password;
    private UserStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public User(UUID id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = Objects.requireNonNull(firstname, "firstname cannot be null");
        this.lastname = Objects.requireNonNull(lastname, "lastname cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.password = Objects.requireNonNull(password, "password cannot be null");
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.deletedAt = null;
    }


    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = Objects.requireNonNull(firstname, "firstname cannot be null");
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = Objects.requireNonNull(lastname, "lastname cannot be null");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password, "password cannot be null");
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

}
