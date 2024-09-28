package fr.ceured.batismart.server.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;

@Service
public class JwtService {

    private final String key = "gpLXlOa1ULCBXR2r9CSj11EH6ysMt2T4";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

    public String generateToken(String username) {
        Date date = new Date();
        return Jwts.builder()
                .setIssuer("Server")
                .setSubject("JWT Auth Token")
                .claim("username", username)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    public Authentication validateToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        String username = claims.getOrDefault("username", "").toString();
        if (StringUtils.hasText(username)) {
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }

}
