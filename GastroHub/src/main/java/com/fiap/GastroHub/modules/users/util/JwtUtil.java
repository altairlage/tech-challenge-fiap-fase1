package com.fiap.GastroHub.modules.users.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private static final Key KEY_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Key getKeySecret() {
        return KEY_SECRET;
    }
}
