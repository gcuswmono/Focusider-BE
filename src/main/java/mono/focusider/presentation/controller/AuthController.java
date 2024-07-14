package mono.focusider.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.application.dto.AuthResponse;
import mono.focusider.application.dto.LoginRequest;
import mono.focusider.application.dto.SignupRequest;
import mono.focusider.application.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {
        @Autowired
        private AuthenticationService authenticationService;

        @Operation(summary = "Sign up a new user", description = "Register a new user with username and password", responses = {
                        @ApiResponse(responseCode = "200", description = "User registered successfully"),
                        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input")
        })
        @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
                log.info("Received signup request: " + signupRequest);
                try {
                        authenticationService.signup(signupRequest.getUsername(), signupRequest.getPassword());
                        return ResponseEntity.ok("User registered successfully");
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }

        @Operation(summary = "Login a user", description = "Authenticate a user and return access and refresh tokens as cookies", responses = {
                        @ApiResponse(responseCode = "200", description = "Login successful"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
        })
        @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
                AuthResponse authResponse = authenticationService.authenticate(loginRequest.getUsername(),
                                loginRequest.getPassword());

                Cookie accessTokenCookie = new Cookie("access_token", authResponse.getAccessToken());
                accessTokenCookie.setHttpOnly(false);
                accessTokenCookie.setSecure(true);
                accessTokenCookie.setPath("/");

                Cookie refreshTokenCookie = new Cookie("refresh_token", authResponse.getRefreshToken());
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setPath("/api/auth/refresh");

                response.addCookie(accessTokenCookie);
                response.addCookie(refreshTokenCookie);

                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Refresh access token", description = "Use a refresh token to obtain a new access token", responses = {
                        @ApiResponse(responseCode = "200", description = "New access token issued"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid refresh token")
        })
        @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token") String refreshToken,
                        HttpServletResponse response) {
                AuthResponse authResponse = authenticationService.refreshToken(refreshToken);

                Cookie accessTokenCookie = new Cookie("access_token", authResponse.getAccessToken());
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setSecure(true);
                accessTokenCookie.setPath("/");

                response.addCookie(accessTokenCookie);

                return ResponseEntity.ok().build();
        }
}