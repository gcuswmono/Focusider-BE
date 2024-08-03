package mono.focusider.domain.auth.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.auth.helper.AuthHelper;
import mono.focusider.domain.auth.validator.AuthValidator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mono.focusider.global.security.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthValidator authValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthHelper authHelper;

    public void signup(SignupRequestDto signupRequestDto) {
        authValidator.checkAccountId(signupRequestDto.accountId());
        String password = passwordEncoder.encode(signupRequestDto.password());
        authHelper.createMemberAndSave(signupRequestDto, password);

    }
//
//    public AuthResponseDto authenticate(String username, String password) {
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> {
//                    log.warn("Authentication attempt for non-existent user: {}", username);
//                    return new UsernameNotFoundException("User not found");
//                });
//
//        if (passwordEncoder.matches(password, member.getPassword())) {
//            String accessToken = jwtTokenUtil.generateAccessToken(username);
//            String refreshToken = jwtTokenUtil.generateRefreshToken(username);
//
//            redisTemplate.opsForValue().set(username, refreshToken);
//
//            log.info("User authenticated successfully: {}", username);
//            return new AuthResponseDto(accessToken, refreshToken);
//        } else {
//            log.warn("Failed authentication attempt for user: {}", username);
//            throw new BadCredentialsException("Invalid credentials");
//        }
//    }
//
//    public AuthResponseDto refreshToken(String refreshToken) {
//        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
//        String storedRefreshToken = redisTemplate.opsForValue().get(username);
//
//        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
//            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
//            log.info("Token refreshed for user: {}", username);
//            return new AuthResponseDto(newAccessToken, refreshToken);
//        } else {
//            log.warn("Invalid refresh token attempt for user: {}", username);
//            throw new InvalidRefreshTokenException("Invalid refresh token");
//        }
//    }
//
//    public boolean isUsernameAvailable(String username) {
//        return !memberRepository.existsByUsername(username);
//    }
}