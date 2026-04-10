package com.gdaniel.glpayroll.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Service
public class JwtUtil {

    @Value("${app.jwtsecret}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days in ms

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // Specify the signing key
                .build() // Build the parser instance
                .parseSignedClaims(token) // Parse the token
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, List<String> roles) {

        return createToken(userDetails.getUsername(), roles);
    }

    private String createToken(String subject, List<String> roles) {
        return Jwts.builder()
                .claim("roles", roles)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        return secretKey;
    }

}
