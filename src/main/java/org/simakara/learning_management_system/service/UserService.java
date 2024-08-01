package org.simakara.learning_management_system.service;

import org.simakara.learning_management_system.dto.request.LoginRequest;
import org.simakara.learning_management_system.dto.request.RegisterRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse login(LoginRequest request);

    String createToken(String username);
}
