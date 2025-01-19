package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.LoginUserRequest;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.modules.users.util.JwtUtil;
import com.fiap.GastroHub.shared.AppException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginUserUseCase {
    private static final Logger logger = LogManager.getLogger(LoginUserUseCase.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginUserUseCase(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginUserRequest loginUserRequest) {
        logger.info("Iniciando o processo de login para o email: {}", loginUserRequest.getEmail());

        User user = userRepository.findUserByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new AppException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        logger.info("Usuário encontrado: {}", user.getName());

        if (user.getPassword().equals(loginUserRequest.getPassword())) {
            logger.info("Senha válida para o usuário: {}", user.getName());

            Key keySecret = JwtUtil.getKeySecret();
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getName());
            claims.put("email", user.getEmail());

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(keySecret)
                    .compact();

            logger.info("Token gerado com sucesso para o usuário: {}", user.getName());
            return jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail());
        } else {
            throw new AppException("Usuário ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }
    }
}
