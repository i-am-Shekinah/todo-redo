package org.todo.repository;


import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.todo.dto.TodoUpdateParams;
import org.todo.model.ApiResponse;
import org.todo.model.Todo;
import org.todo.model.TodoStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TodoRepository {
    private final UserRepository userRepository;
    Map<UUID, Map<UUID, Todo>> userToTodos =  new HashMap<>();

    public TodoRepository(UserRepository userRepository) {
        this.userRepository = Preconditions.checkNotNull(userRepository,  "userRepository cannot be null");
    }

    public ApiResponse<Map<UUID, Todo>> saveTodo(UUID userId, UUID todoId, Todo todo) {
        Preconditions.checkNotNull(userId, "userId cannot be null");
        Preconditions.checkNotNull(todoId, "todoId cannot be null");
        Preconditions.checkArgument(todo != null, "todo cannot be null");
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot save Todo: User with ID '%s' does not exist.",
                userId
        );

        // get the inner map for a user, or create a new one if absent
        Map<UUID, Todo> userTodos = userToTodos.computeIfAbsent(
                userId,
                k -> new HashMap<>()
        );

        userTodos.put(todoId, todo);

        return new ApiResponse<>(
                true,
                "Todo saved successfully",
                userTodos
                );
    }


    public ApiResponse<Todo> fetchTodo(UUID userId, UUID todoId) {
        // check input integrity
        Preconditions.checkNotNull(userId, "userId cannot be null");
        Preconditions.checkNotNull(todoId, "todoId cannot be null");

        // check user exists
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot fetch Todo: User with ID '%s' does not exist",
                userId
        );

        // check todo exists
        Preconditions.checkArgument(
                userToTodos.get(userId).get(todoId) != null,
                "Cannot fetch Todo: Todo with ID '%s' does not exist",
                todoId
        );

        Todo todo = userToTodos.get(userId).get(todoId);

        return  new ApiResponse<>(
                true,
                "Todo fetched successfully",
                todo
        );
    }


    public ApiResponse<Todo> updateTodo(UUID userId, UUID todoId, TodoUpdateParams updateParams) {
        Preconditions.checkNotNull(userId, "userId cannot be null");
        Preconditions.checkNotNull(todoId, "todoId cannot be null");

        Todo todo = fetchTodo(userId, todoId).getData();

        // check if user exists
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot update todo: User with ID '%s' does not exist",
                userId
        );

        // check if todo exists
        Preconditions.checkArgument(
                todo != null,
                "Cannot update Todo: Todo with ID '%s' does not exist",
                todoId
        );

        if (updateParams.getTitle() != null) {
            todo.setTitle(updateParams.getTitle());
        }

        if (updateParams.getDescription() != null) {
            todo.setDescription(updateParams.getDescription());
        }

        if (updateParams.getDeadline() != null) {
            todo.setDeadline(updateParams.getDeadline());
        }

        if (updateParams.getStatus() != null) {
            switch (updateParams.getStatus()) {
                case COMPLETED -> todo.markCompleted();
                case ACTIVE -> todo.markActive();
                case DELETED -> todo.markDeleted();
                case OVERDUE -> todo.markOverdue();
            }
        }

        todo.setUpdatedAt();

        return new ApiResponse<>(
                true,
                "Todo udpated successfully",
                todo
        );
    }


    public ApiResponse<Map<UUID, Todo>> deleteTodo(UUID userId, UUID todoId) {
        Preconditions.checkNotNull(userId, "userId cannot be null");
        Preconditions.checkNotNull(todoId, "todoId cannot be null");

        // check if user exists
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot delete Todo: User with ID '%s' does not exist",
                userId
        );

        Todo todo =  fetchTodo(userId, todoId).getData();

        // check if todo exists
        Preconditions.checkArgument(
                todo != null,
                "Cannot delete todo: Todo with ID '%s' does not exist",
                todoId
        );

        todo.markDeleted();

        return new ApiResponse<>(
                true,
                "Todo deleted successfully",
                userToTodos.get(userId)
        );
    }


    public ApiResponse<Map<UUID, Todo>> fetchAllTodos(UUID userId) {
        Preconditions.checkNotNull(userId, "userId cannot be null");

        // check user exists
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot fetch Todos: User with ID '%s' does not exist",
                userId
        );

        return new ApiResponse<>(
                true,
                "Fetched all todos successfully",
                userToTodos.get(userId)
        );
    }

    public ApiResponse<Map<UUID, Todo>> fetchActiveTodos(UUID userId) {
        Preconditions.checkNotNull(userId, "userId cannot be null");

        // check user exists
        Preconditions.checkArgument(
                userRepository.userExists(userId),
                "Cannot fetch Todos: User with ID '%s' does not exist",
                userId
        );

        Map<UUID, Todo> todos =  userToTodos.get(userId);

        Predicate<Map.Entry<UUID, Todo>> isActive = entry ->
                TodoStatus.ACTIVE.equals(entry.getValue().getStatus());

        Map<UUID, Todo> activeTodos = CollectionUtils.select(todos.entrySet(), isActive)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new ApiResponse<>(
                true,
                "Fetched all active todos successfully",
                activeTodos
        );
    }

}
