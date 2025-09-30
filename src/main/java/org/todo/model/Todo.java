package org.todo.model;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Todo {
    private final UUID id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TodoStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    private Todo(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.deadline = builder.deadline;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    // Builder class
    public static class Builder {

        // compulsory fields
        private final UUID id;
        private String title;

        // optional fields
        private String description;
        private LocalDateTime deadline;
        private TodoStatus status;

        // timestamps
        private final LocalDateTime createdAt;
        private LocalDateTime updatedAt = null;
        private LocalDateTime deletedAt = null;


        // Builder constructor
        public Builder (UUID id, String title) {
            this.id = Objects.requireNonNull(id, "id cannot be null");
            this.title = Objects.requireNonNull(title, "title cannot be null");
            this.createdAt = LocalDateTime.now();
            this.status = TodoStatus.ACTIVE;
        }

        // optional methods
        public Builder description(String description) {
            this.description = Objects.requireNonNull(description, "description cannot be null");
            return this;
        }

        public Builder deadline(LocalDateTime deadline) {
            this.deadline = Objects.requireNonNull(deadline, "deadline cannot be null");
            return this;
        }


        public Todo build() {
            return new Todo(this);
        }
    }


    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description cannot be null");
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = Objects.requireNonNull(deadline, "deadline cannot be null");
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void markDeleted() {
        this.status =  TodoStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    public void markOverdue() {
        this.status = TodoStatus.OVERDUE;
    }

    public void markActive() {
        this.status = TodoStatus.ACTIVE;
    }

    public void markCompleted() {
        this.status = TodoStatus.COMPLETED;
    }

    public TodoStatus getStatus() {
        return status;
    }
}
