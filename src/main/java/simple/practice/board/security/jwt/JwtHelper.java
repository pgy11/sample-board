package simple.practice.board.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import simple.practice.board.contoller.dto.UserSignInDto;
import simple.practice.board.contoller.dto.UserSignUpDto;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JwtHelper {

    public static final long TOKEN_VALIDATION_SECONDE = 1000L * 10;
    public static final long REFRESH_TOKEN_VALIDATION_SECONDE = 1000L * 60 * 24 * 2;

    public static final String ACCESS_TOKEN_NAME = "accessToken";
    public static final String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(this.secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {
        return extractAllClaims(token).get("userEmail", String.class);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(UserSignInDto userSignInDto) {
        return doGenerateToken(userSignInDto.getPassword(), REFRESH_TOKEN_VALIDATION_SECONDE);
    }

    public String doGenerateToken(String userEmail, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(this.secretKey), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String userEmail = getUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }



}
