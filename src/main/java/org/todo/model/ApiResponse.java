package org.todo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A generic API response wrapper that ensures all responses
 * follow a consistent structure
 * @param <T> the type of the data payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields from JSON
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // getters & setters
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
