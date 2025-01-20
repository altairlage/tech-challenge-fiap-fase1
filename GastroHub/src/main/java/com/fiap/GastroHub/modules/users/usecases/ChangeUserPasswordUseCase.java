package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.ChangeUserPasswordRequest;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import com.fiap.GastroHub.shared.infra.beans.LogBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ChangeUserPasswordUseCase {
    private static final Logger logger = LogManager.getLogger(ChangeUserPasswordUseCase.class);

    private final UserRepository userRepository;

    public ChangeUserPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Executes the user creation use case
     *
     * @param changeUserPasswordRequest Object containing the user info
     * @return Response object with user created successfully
     **/
    @LogBean
    public void execute(Long id, ChangeUserPasswordRequest changeUserPasswordRequest) {
        logger.info("Trying to change password from user with id: {}", id);

       try {
           User userFromDb = userRepository.findUserById(id);
           if (userFromDb != null) {
               if (userFromDb.getPassword().equals(changeUserPasswordRequest.getCurrentPassword())) {
                   userFromDb.setPassword(changeUserPasswordRequest.getNewPassword());
                   userFromDb.setLastUpdatedAt(Date.from(Instant.now()));

                   User updatedUser = userRepository.save(userFromDb);
               }else{
                   throw new RuntimeException("Password does not match");
               }

           } else {
               throw new RuntimeException("User not found");
           }

           logger.info("Password updated successfully");
       } catch (Exception e) {
           throw new AppException(String.format("Failed to update user with id %d", id), HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
}
