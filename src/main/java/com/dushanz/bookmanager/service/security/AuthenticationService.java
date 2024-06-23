package com.dushanz.bookmanager.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service class responsible for authentication/authorization related functions.
 */
@Service
public class AuthenticationService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Getter
    @Value("${security.jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(String username) {

        byte[] encodedSecretKey = Base64.getEncoder().encode(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(encodedSecretKey))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        byte[] encodedSecretKey = Base64.getEncoder().encode(jwtSecret.getBytes());
        return Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        byte[] encodedSecretKey = Base64.getEncoder().encode(jwtSecret.getBytes());
        try {
            Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenExpiryDate() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.getJwtExpiration());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(expiryDate);
    }

}


