package mono.focusider.domain.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.AuthResponseDto;
import mono.focusider.domain.member.domain.User;
import mono.focusider.domain.member.repository.UserRepository;
import mono.focusider.global.Security.JwtTokenUtil;
import mono.focusider.global.exception.InvalidRefreshTokenException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtTokenUtil jwtTokenUtil, RedisTemplate<String, String> redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisTemplate = redisTemplate;
    }

    public void signup(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            log.error("Signup attempt with null or empty username");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            log.error("Signup attempt with null or empty password");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            log.warn("Signup attempt with existing username: {}", username);
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("New user signed up: {}", username);
    }

    public AuthResponseDto authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Authentication attempt for non-existent user: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        if (passwordEncoder.matches(password, user.getPassword())) {
            String accessToken = jwtTokenUtil.generateAccessToken(username);
            String refreshToken = jwtTokenUtil.generateRefreshToken(username);

            redisTemplate.opsForValue().set(username, refreshToken);

            log.info("User authenticated successfully: {}", username);
            return new AuthResponseDto(accessToken, refreshToken);
        } else {
            log.warn("Failed authentication attempt for user: {}", username);
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        String storedRefreshToken = redisTemplate.opsForValue().get(username);

        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
            log.info("Token refreshed for user: {}", username);
            return new AuthResponseDto(newAccessToken, refreshToken);
        } else {
            log.warn("Invalid refresh token attempt for user: {}", username);
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
}