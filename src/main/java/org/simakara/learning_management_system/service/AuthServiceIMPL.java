package org.simakara.learning_management_system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simakara.learning_management_system.dto.request.LoginRequest;
import org.simakara.learning_management_system.dto.request.RegisterRequest;
import org.simakara.learning_management_system.dto.response.UserResponse;
import org.simakara.learning_management_system.handler.ValidatorHandler;
import org.simakara.learning_management_system.model.Role;
import org.simakara.learning_management_system.model.User;
import org.simakara.learning_management_system.repository.RoleRepository;
import org.simakara.learning_management_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Set;

import static org.simakara.learning_management_system.mapper.UserResponseMapper.toUserResponse;
import static org.simakara.learning_management_system.tools.NameFormater.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {

    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    private final ValidatorHandler validatorHandler;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {
        validatorHandler.validate(request);

        if (
                userRepo.existsByUsername(request.username()) &&
                        userRepo.existsByEmail(request.email())
        ) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username and email is already used"
            );
        } else if (userRepo.existsByUsername(request.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username is already used"
            );
        } else if (userRepo.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already used"
            );
        }

        Role role = roleRepo
                .findByName("USER")
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Role does not exist"
                        )
                );

        User registeredUser = User
                .builder()
                .firstname(format(request.firstname()))
                .lastname(format(request.lastname()))
                .username(request.username().toLowerCase())
                .email(request.email())
                .roles(Set.of(role))
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepo.save(registeredUser);

        return toUserResponse(registeredUser);
    }

    @Override
    public UserResponse login(LoginRequest request) {

        validatorHandler.validate(request);

        var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username().toLowerCase(),
                        request.password()
                )
        );

        User user = (User) auth.getPrincipal();

        return toUserResponse(user);
    }

    @Override
    public String createToken(String username) {

        User user = userRepo.findByUsername(username.toLowerCase())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        var claims = new HashMap<String, Object>();

        claims.put("fullName", user.fullName());

        return jwtService.generateToken(claims, user);
    }
}
