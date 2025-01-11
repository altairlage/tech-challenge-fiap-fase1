package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.infra.beans.LogBean;
import com.fiap.GastroHub.shared.infra.crypto.AesCryptoImp;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private static final Logger logger = LogManager.getLogger(CreateUserUseCase.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private AesCryptoImp aesCrypto;

    /**
     * Executes the user creation use case
     *
     * @param request Object containing the user info
     * @return Response object with user created successfully
     **/
    @LogBean
    @Transactional
    public UserResponse execute(CreateUpdateUserRequest request) {
        logger.info("Trying to create a new user with the following info: {}", request.getName());

        User user = modelMapper.map(request, User.class);
        user = userRepository.save(user);
        logger.info("New user created successfully");

        return modelMapper.map(user, UserResponse.class);
    }
}
