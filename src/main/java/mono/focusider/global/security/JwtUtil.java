package mono.focusider.global.security;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.global.utils.cookie.CookieUtils;
import mono.focusider.global.utils.redis.RedisExpiredData;
import mono.focusider.global.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static mono.focusider.global.security.JwtEnum.*;

@Getter
@RequiredArgsConstructor
@Component
public class JwtUtil {
    private SecretKey secretKey;

    @Value("${spring.jwt.secret}")
    private String key;
    @Value("${spring.jwt.expireAccessToken}")
    private long accessTokenExpireTime;
    @Value("${spring.jwt.expireRefreshToken}")
    private long refreshTokenExpireTime;
    private final RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String createAccessToken(AuthUserInfo authUserInfo) {
        return createToken(authUserInfo, accessTokenExpireTime);
    }

    public String createRefreshToken(AuthUserInfo authUserInfo) {
        return createToken(authUserInfo, refreshTokenExpireTime);
    }

    private String createToken(AuthUserInfo authUserInfo, Long expireTime) {
        return Jwts.builder()
                .claim("memberId", authUserInfo.memberId())
                .claim("level", authUserInfo.level())
                .claim("memberRole", authUserInfo.memberRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(secretKey)
                .compact();

    }

    public Long getMemberId(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }

    public String getMemberName(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("name", String.class);
    }

    public String getMemberGender(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("gender", String.class);
    }

    public Integer getMemberLevel(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("level", Integer.class);
    }

    public String getMemberRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("memberRole", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public void addAccessTokenToCookie(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = CookieUtils.createCookie(ACCESS_TOKEN_NAME.getDesc(), accessToken);
        CookieUtils.addCookieWithMaxAge(response, accessTokenCookie, Math.toIntExact(accessTokenExpireTime));
    }

    public void addRedisTokenInfo(String refreshToken, String accessToken) {
        redisUtils.setDataWithExpireTime(RedisExpiredData.ofToken(accessToken, refreshToken, refreshTokenExpireTime));
    }
}