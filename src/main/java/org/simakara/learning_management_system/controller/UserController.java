package org.simakara.learning_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.simakara.learning_management_system.dto.request.UpdateUserRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.simakara.learning_management_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/api/v2/user"
)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(
            path = "/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<UserResponse>> getUser(
            @PathVariable(value = "username") String username
    ) {
        UserResponse response = userService.getUser(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PatchMapping(
            path = "/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<UserResponse>> updateUser(
            @PathVariable(value = "username") String username,
            @RequestBody UpdateUserRequest request
    ) {
        UserResponse response = userService.updateUser(request, username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @DeleteMapping(
            path = "/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deleteUser(String username) {
        userService.deleteUser(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                "User deleted",
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }
}
