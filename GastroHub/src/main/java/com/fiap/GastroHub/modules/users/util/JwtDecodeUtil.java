package com.fiap.GastroHub.modules.users.util;

import com.fiap.GastroHub.modules.users.dtos.JwtUserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class JwtDecodeUtil {
    static Key keySecret = JwtUtil.getKeySecret();

    private JwtDecodeUtil(){

    }

    public static JwtUserRequest decodeToken(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(keySecret)
                .build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        JwtUserRequest jwtDto = new JwtUserRequest();
        jwtDto.setId(claims.get("id", Integer.class));
        jwtDto.setUsername(claims.get("username", String.class));
        jwtDto.setEmail(claims.get("email", String.class));

        return jwtDto;
    }
}
