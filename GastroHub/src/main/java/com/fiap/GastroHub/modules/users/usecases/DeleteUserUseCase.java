package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private static final Logger logger = LogManager.getLogger(DeleteUserUseCase.class);

    @Autowired
    private UserRepository userRepository;

    public void DeleteUserUseCase(Long id) {
        if (!userRepository.existsById(id)) {
            throw new AppException("Usuário não encontrado", HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
    }   
}
