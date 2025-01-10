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
import java.util.Optional;

@Service
public class UpdateUserUseCase {
    private static final Logger logger = LogManager.getLogger(UpdateUserUseCase.class);

    private final UserRepository userRepository;
    private AesCryptoImp aesCrypto;

    public UpdateUserUseCase(UserRepository userRepository) {
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
    public void execute(Long id, CreateUpdateUserRequest createUpdateUserRequest) {
        logger.info("Trying to update a user with the following id: {}", id);

        try {
            User userFromDb = userRepository.findUserById(id);
            if (userFromDb != null) {
                userFromDb.setName(createUpdateUserRequest.getName());
                userFromDb.setUsername(createUpdateUserRequest.getUsername());
                userFromDb.setAddress(createUpdateUserRequest.getAddress());
                userFromDb.setEmail(createUpdateUserRequest.getEmail());
                userFromDb.setPassword(this.aesCrypto.encrypt(createUpdateUserRequest.getPassword()));
                userFromDb.setLastUpdatedAt(Date.from(Instant.now()));

                User updatedUser = userRepository.save(userFromDb);
            } else {
                throw new RuntimeException("User not found");
            }

            logger.info("User updated successfully");

        } catch (Exception e) {
            throw new AppException(String.format("Failed to update user with id %d", id), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
