package mono.focusider.global.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.domain.auth.mapper.AuthMapper;
import mono.focusider.domain.member.type.MemberRole;
import mono.focusider.global.error.code.GlobalErrorCode;
import mono.focusider.global.error.exception.ForbiddenException;
import mono.focusider.global.error.exception.UnauthorizedException;
import mono.focusider.global.utils.cookie.CookieUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final static String COOKIE_TYPE = "accessToken";
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;
//    private final RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            checkCookieAndUpdateToken(request, response);
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(GlobalErrorCode.UNAUTHORIZED);
        } catch (AccessDeniedException e) {
            throw new ForbiddenException(GlobalErrorCode.FORBIDDEN);
        }
    }

    private void checkCookieAndUpdateToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = CookieUtils.getCookieValueWithName(request, COOKIE_TYPE);
        if (!Objects.isNull(authorization))
            updateAccessToken(authorization, response);
    }

    private void updateAccessToken(String authorization, HttpServletResponse response) {
        try {
            AuthUserInfo authUserInfo = createAuthUserInfoDtoForToken(authorization);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authUserInfo, null, authUserInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            log.info("엑세스 토큰 만료");
//            String refreshToken = (String) redisUtils.getData(authorization);
//            log.info("refreshToken: {}", refreshToken);
//            validateRefreshToken(refreshToken);
//            generateRefreshTokenWithAccessToken(refreshToken, response);
        }
    }

    private AuthUserInfo createAuthUserInfoDtoForToken(String token) {
        Long memberId = jwtUtil.getMemberId(token);
        String name = jwtUtil.getMemberName(token);
        String gender = jwtUtil.getMemberGender(token);
        Integer level = jwtUtil.getMemberLevel(token);
        System.out.println(jwtUtil.getMemberRole(token));
        MemberRole memberRole = MemberRole.valueOf(jwtUtil.getMemberRole(token));
        return authMapper.toAuthUserInfoWithToken(memberId, name, gender, level, memberRole);
    }

//    private String refreshAccessToken(String refreshToken, AuthUserInfo authUserInfo, HttpServletResponse response) {
//        String accessToken = jwtUtil.createAccessToken(authUserInfo);
//        jwtUtil.addAccessTokenToCookie(response, accessToken);
//        jwtUtil.addRedisTokenInfo(refreshToken, accessToken);
//        return accessToken;
//    }

//    private void refreshRedisToken(String accessToken, AuthUserInfo authUserInfo) {
//        redisUtils.deleteData(accessToken);
//        String refreshToken = jwtUtil.createRefreshToken(authUserInfo);
//        Long refreshTokenExpiredTime = jwtUtil.getRefreshTokenExpireTime();
//        redisUtils.setDataWithExpireTime(accessToken, refreshToken, refreshTokenExpiredTime);
//    }

//    private void generateRefreshTokenWithAccessToken(String refreshToken, HttpServletResponse response) {
//        if (jwtUtil.isExpired(refreshToken)){
//            return;
//        }
//        AuthUserInfo authUserInfo = createAuthUserInfoDtoForToken(refreshToken);
//        String accessToken = refreshAccessToken(refreshToken, authUserInfo, response);
//        refreshRedisToken(accessToken, authUserInfo);
//        log.info("엑세스 토큰 재발급 = {}", accessToken);
//    }
//
//    private void validateRefreshToken(String refreshToken) {
//        if (Objects.isNull(refreshToken))
//            throw new InvalidValueException(GlobalErrorCode.NOT_REFRESH_TOKEN);
//    }
}