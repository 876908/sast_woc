package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"SW1oYW5kc29tZQ==SW1oYW5kc29tZQ==SW1oYW5kc29tZQ==")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .compact();
    }
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey("SW1oYW5kc29tZQ==SW1oYW5kc29tZQ==SW1oYW5kc29tZQ==")
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
