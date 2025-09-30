package org.todo;


import org.todo.model.Todo;
import org.todo.model.TodoStatus;

import java.util.UUID;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Todo todo = new Todo.Builder(UUID.randomUUID(), "Do the laundry").build();

        Todo todo2 = new Todo.Builder(UUID.randomUUID(), "Code for 30 mins")
                        .description("Learn about optional params in Java")
                        .deadline(LocalDateTime.parse("2025-10-22T14:30:00"))
                        .build();

        System.out.println(todo.getTitle());
        System.out.println(todo.getStatus());

        System.out.println(todo2.getDeadline());
        System.out.println(todo2.getDescription());
        System.out.println(todo2.getTitle());
        System.out.println(todo2.getStatus());
    }
}