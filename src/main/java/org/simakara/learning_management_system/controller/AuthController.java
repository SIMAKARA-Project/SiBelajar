package org.simakara.learning_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.simakara.learning_management_system.dto.request.LoginRequest;
import org.simakara.learning_management_system.dto.request.RegisterRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.simakara.learning_management_system.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/api/v2/auth"
)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<UserResponse>> register(
            @RequestBody RegisterRequest request
    ) {

        UserResponse response = authService.register(request);

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

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<UserResponse>> login(
            @RequestBody LoginRequest request
    ) {
        UserResponse response = authService.login(request);

        String token = authService.createToken(request.username());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token))
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }
}
