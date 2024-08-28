package mono.focusider.application.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.req.CheckDuplicatedReqDto;
import mono.focusider.domain.auth.dto.req.LoginReqDto;
import mono.focusider.domain.auth.dto.req.SignupReqDto;
import mono.focusider.domain.auth.dto.res.CheckDuplicatedResDto;
import mono.focusider.domain.auth.service.AuthService;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API")
@RequiredArgsConstructor
public class AuthController {
        private final AuthService authService;

        @Operation(summary = "회원가입", description = "아이디 비밀번호 기반 회원가입", responses = {
                        @ApiResponse(responseCode = "200", description = "User registered successfully"),
                        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input")
        })
        @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<SuccessResponse<?>> signup(@Valid @RequestBody SignupReqDto requestDto,
                                                         HttpServletResponse response) {
                authService.signup(requestDto, response);
                return SuccessResponse.ok(null);
        }

        @Operation(summary = "로그인", description = "아아디 비밀번호 기반 로그인, accessToken은 쿠키로, refreshToken은 redis", responses = {
                        @ApiResponse(responseCode = "200", description = "Login successful"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
        })
        @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> login(@RequestBody LoginReqDto loginRequest, HttpServletResponse response) {
                authService.login(loginRequest, response);
                return SuccessResponse.ok(null);
        }

        @Operation(summary = "아이디 중복 체크", description = "아이디 중복 체크", responses = {
                @ApiResponse(responseCode = "200", description = "중복 여부"),
                @ApiResponse(responseCode = "500", description = "서버 에러")
        })
        @PostMapping("/duplicated")
        public ResponseEntity<SuccessResponse<?>> checkDuplicatedAccountId(@RequestBody CheckDuplicatedReqDto reqDto) {
                CheckDuplicatedResDto result = authService.checkDuplicated(reqDto);
                return SuccessResponse.ok(result);
        }

        @Operation(summary = "로그 아웃", description = "로그 아웃", responses = {
                @ApiResponse(responseCode = "200", description = "로그 아웃"),
                @ApiResponse(responseCode = "500", description = "에러")
        })
        @GetMapping("/logout")
        public ResponseEntity<SuccessResponse<?>> logout(HttpServletRequest request, HttpServletResponse response) {
                authService.logout(request, response);
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