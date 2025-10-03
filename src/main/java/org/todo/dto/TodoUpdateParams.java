package org.todo.dto;

import org.todo.model.TodoStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public class TodoUpdateParams {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TodoStatus status;

    /*
    public TodoUpdateParams(
            Optional<String> title,
            Optional<String> description,
            Optional<LocalDateTime> deadline,
            Optional<TodoStatus> status
    ) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

     */

    // getters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}
