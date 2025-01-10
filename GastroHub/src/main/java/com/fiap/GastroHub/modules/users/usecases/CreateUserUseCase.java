package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import com.fiap.GastroHub.shared.infra.beans.LogBean;
import com.fiap.GastroHub.shared.infra.crypto.AesCryptoImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class CreateUserUseCase {
    private static final Logger logger = LogManager.getLogger(CreateUserUseCase.class);

    private final UserRepository userRepository;
    private AesCryptoImp aesCrypto;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.aesCrypto = new AesCryptoImp();
    }


    /**
     * Executes the user creation use case
     *
     * @param createUpdateUserRequest Object containing the user info
     * @return Response object with user created successfully
     **/
    @LogBean
    public CreateUpdateUserResponse execute(CreateUpdateUserRequest createUpdateUserRequest) {
        logger.info("Trying to create a new user with the following info: {}", createUpdateUserRequest.toString());
        try {
            User newUser = new User();
            newUser.setName(createUpdateUserRequest.getName());
            newUser.setUsername(createUpdateUserRequest.getUsername());
            newUser.setAddress(createUpdateUserRequest.getAddress());
            newUser.setEmail(createUpdateUserRequest.getEmail());
            newUser.setPassword(this.aesCrypto.encrypt(createUpdateUserRequest.getPassword()));
            newUser.setCreatedAt(Date.from(Instant.now()));
            newUser.setLastUpdatedAt(Date.from(Instant.now()));

            User createdUser = userRepository.save(newUser);

            logger.info("New user created successfully");

            return new CreateUpdateUserResponse(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail());
        } catch (Exception e) {
            throw new AppException("Failed to create a new user", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
