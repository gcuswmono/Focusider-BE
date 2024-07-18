package mono.focusider.global.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final JwtSecretKey jwtSecretKey;

    @Value("${jwt.access.expiration}")
    private long accessTokenValidity;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidity;

    public JwtTokenUtil(JwtSecretKey jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getSecretKey().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getSecretKey().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getSecretKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}