package mono.focusider.domain.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.domain.auth.dto.req.LoginRequestDto;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.auth.helper.AuthHelper;
import mono.focusider.domain.auth.mapper.AuthMapper;
import mono.focusider.domain.auth.validator.AuthValidator;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
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
    private final MemberHelper memberHelper;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthHelper authHelper;
    private final AuthMapper authMapper;

    public void signup(SignupRequestDto signupRequestDto) {
        authValidator.checkAccountId(signupRequestDto.accountId());
        String password = passwordEncoder.encode(signupRequestDto.password());
        authHelper.createMemberAndSave(signupRequestDto, password);
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberHelper.findMemberByAccountIdOrThrow(loginRequestDto.accountId());
        authValidator.validatePassword(loginRequestDto.password(), member.getPassword(), passwordEncoder);
        AuthUserInfo authUserInfo = AuthUserInfo.of(member);
        String accessToken = jwtUtil.createAccessToken(authUserInfo);
        String refreshToken = jwtUtil.createRefreshToken(authUserInfo);
        jwtUtil.addRedisTokenInfo(refreshToken, accessToken);
        jwtUtil.addAccessTokenToCookie(response, accessToken);
    }
}