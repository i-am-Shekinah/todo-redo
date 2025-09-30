package org.todo.repository;

import com.google.common.base.Preconditions;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.todo.model.ApiResponse;
import org.todo.model.User;
import org.todo.model.UserStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserRepository {
    Map<UUID, User> users = new HashMap<>();
    BidiMap<String, UUID> emailToUserId = new DualHashBidiMap<>();
    Map<UUID, User> activeUsers = filterActiveUser(users);

    public ApiResponse<User> saveUser(User user) {
        // check data integrity
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(user.getId(), "id cannot be null");
        Preconditions.checkNotNull(user.getEmail(), "email cannot be null");
        Preconditions.checkNotNull(user.getFirstname(), "firstname cannot be null");
        Preconditions.checkNotNull(user.getLastname(), "lastname cannot be null");
        Preconditions.checkNotNull(user.getPassword(), "password cannot be null");

        // check if user exists
        Preconditions.checkArgument(activeUsers.get(user.getId()) == null, "User with id %s already exists", user.getId());
        Preconditions.checkArgument(emailToUserId.get(user.getEmail()) == null, "User with email %s already exists", user.getEmail());

        // save user
        users.put(user.getId(), user);

        return new ApiResponse<>(
                true,
                "User saved successfully",
                user
        );
    }

    public ApiResponse<User> getUserById(UUID id) {
        // check that user exists
        Preconditions.checkArgument(id != null, "id cannot be null");
        Preconditions.checkArgument(activeUsers.containsKey(id), "User with id %s does not exist", id);

        try {
            User user = activeUsers.get(id);
            return new ApiResponse<>(
                    true,
                    "Fetched user by id successfully",
                    user
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }
    }

    public ApiResponse<User> getUserByEmail(String email) {
        Preconditions.checkArgument(email != null, "email cannot be null");
        Preconditions.checkArgument(emailToUserId.containsKey(email), "User with email %s does not exist", email);

        try {
            User user = activeUsers.get(emailToUserId.get(email));
            return new ApiResponse<>(
                    true,
                    "Fetched user by email successfully",
                    user
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }
    }

    // soft delete
    public ApiResponse<Map<UUID, User>> deleteUserById(UUID id) {
        // check that user exists
        Preconditions.checkArgument(id != null, "id cannot be null");
        Preconditions.checkArgument(activeUsers.containsKey(id), "User with id %s does not exist", id);

        try {
            activeUsers.get(id).setStatus(UserStatus.DELETED);
            return new ApiResponse<>(
                    true,
                    "User deleted successfully",
                    activeUsers
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    false,
                    "Failed to delete user by id - User does not exist",
                    null
            );
        }
    }

    // soft delete
    public ApiResponse<Map<UUID, User>> deleteUserByEmail(String email) {
        Preconditions.checkArgument(email != null, "email cannot be null");
        Preconditions.checkArgument(emailToUserId.containsKey(email), "User with email %s does not exist", email);

        try {
            activeUsers.get(emailToUserId.get(email)).setStatus(UserStatus.DELETED);
            return new ApiResponse<>(
                    true,
                    "User deleted successfully",
                    activeUsers
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    false,
                    "Failed to delete user by email - User does not exist",
                    null
            );
        }
    }

    // filter active users
    public static Map<UUID, User> filterActiveUser(Map<UUID, User> allUsers) {
        // Predicate that only selects active users
        Predicate<Map.Entry<UUID, User>> isActive = entry ->
                UserStatus.ACTIVE.equals(entry.getValue().getStatus());

        // select returns a new Collection, so we need to collect into a new map
        return CollectionUtils.select(allUsers.entrySet(), isActive)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
