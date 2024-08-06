package org.simakara.learning_management_system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/api/v2/auth/login"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v2/course/create",
                                        "/api/v2/quiz/create"
                                )
                                .hasAuthority("SUPER_ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v2/course/delete/{code}",
                                        "/api/v2/quiz/delete/{code}",
                                        "/api/v2/user/delete/**"
                                )
                                .hasAuthority("SUPER_ADMIN")
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/v2/course/update/{code}",
                                        "/api/v2/quiz/update/{code}"
                                )
                                .hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v2/quiz/update/{courseCode}",
                                        "/api/v2/quiz/remove/{courseCode}/{quizCode}",
                                        "/api/v2/auth/register",
                                        "/api/v2/course/enroll",
                                        "/api/v2/course/enroll/remove"
                                )
                                .hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v2/course/{code}",
                                        "/api/v2/course/search",
                                        "/api/v2/quiz/{code}",
                                        "/api/v2/quiz/search",
                                        "/api/v2/quiz/course/{courseCode}",
                                        "/api/v2/user/**"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v2/auth/login"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/api/v2/user/update/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
