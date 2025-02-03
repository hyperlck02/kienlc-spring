package org.kienlc.kienlc.authen.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.kienlc.kienlc.authen.model.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(LoginRequest request) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(request.getUsername())
                .issuer("LCK")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000))
                .and()
                .signWith(this.generateKey(secretKey))
                .compact();
    }

    private SecretKey generateKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims payload = Jwts.parser()
                .verifyWith(generateKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(payload);
    }

    public boolean isTokenValid(String token, String userName, UserDetails userDetails) {
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date tokenDateExpired = extractClaim(token, Claims::getExpiration);
        return tokenDateExpired.before(new Date());
    }
}
