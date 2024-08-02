package org.simakara.learning_management_system.service;

import org.simakara.learning_management_system.dto.request.UpdateUserRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;

public interface UserService {

    UserResponse getUser(String username);

    UserResponse updateUser(UpdateUserRequest request, String currentUsername);

    void deleteUser(String username);
}
