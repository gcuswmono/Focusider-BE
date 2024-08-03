package mono.focusider.application.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.req.LoginRequestDto;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.auth.service.AuthService;

import mono.focusider.global.domain.SuccessResponse;
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
        private AuthService authService;

        @Operation(summary = "Sign up a new user", description = "Register a new user with username and password", responses = {
                        @ApiResponse(responseCode = "200", description = "User registered successfully"),
                        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input")
        })
        @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<SuccessResponse<?>> signup(@Valid @RequestBody SignupRequestDto signupRequest) {
                authService.signup(signupRequest);
                return SuccessResponse.ok(null);
        }

        @Operation(summary = "Login a user", description = "Authenticate a user and return access and refresh tokens as cookies", responses = {
                        @ApiResponse(responseCode = "200", description = "Login successful"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
        })
        @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
                authService.login(loginRequest, response);
                return SuccessResponse.ok(null);
        }

//        @Operation(summary = "Refresh access token", description = "Use a refresh token to obtain a new access token", responses = {
//                        @ApiResponse(responseCode = "200", description = "New access token issued"),
//                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid refresh token")
//        })
//        @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
//        public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token") String refreshToken,
//                        HttpServletResponse response) {
//                AuthResponseDto authResponseDto = authService.refreshToken(refreshToken);
//
//                Cookie accessTokenCookie = new Cookie("access_token", authResponseDto.getAccessToken());
//                accessTokenCookie.setHttpOnly(true);
//                accessTokenCookie.setSecure(true);
//                accessTokenCookie.setPath("/");
//
//                response.addCookie(accessTokenCookie);
//
//                return ResponseEntity.ok().build();
//        }
//
//        @GetMapping("/check-username")
//        public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username) {
//                boolean isAvailable = authService.isUsernameAvailable(username);
//                return ResponseEntity.ok().body(new UsernameAvailabilityResponseDto(isAvailable));
//        }
}