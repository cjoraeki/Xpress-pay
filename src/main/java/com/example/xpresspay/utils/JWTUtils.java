package com.example.xpresspay.utils;

import com.example.xpresspay.controller.CustomerController;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class JWTUtils {
    Logger logger = Logger.getLogger(String.valueOf(CustomerController.class));
//    Logger logger = Logger.getLogger(String.valueOf(AirtimeController.class));
    private static final String JWT_SECRET = Dotenv.load().get("JWT_SECRET");

    public String extractUsername(String token){
//        Object Claims;
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims;
        try {

            claims =  Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
            log.info(claims.toString());
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
        return claims;
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    String extractRoleClaim(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);

            return claims.getBody().get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        log.info("Generate token method hit.");
        return createToken(claims, user);
    }

    private String createToken(Map<String, Object> claims,  User user) {
//         claims = Jwts.claims().setSubject(authenticationContext.getUsername());
        claims.put("role", user.getRole());
        log.info("User role -> {}", user.getRole());
        log.info(JWT_SECRET);
        var jwts = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
        log.info("Jwts created: {}", jwts);
        return jwts;
    }

    private Key getSigningKey() {
        log.info("SECRET_KEY -> {}" ,JWT_SECRET);
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
