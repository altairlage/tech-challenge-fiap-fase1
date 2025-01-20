package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.Role;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import com.fiap.GastroHub.shared.config.SecurityConfiguration;
import com.fiap.GastroHub.shared.infra.beans.LogBean;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private static final Logger logger = LogManager.getLogger(CreateUserUseCase.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private SecurityConfiguration securityConfiguration;

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

        try {
            User user = modelMapper.map(request, User.class);
            user.setPassword(securityConfiguration.passwordEncoder().encode(request.getPassword()));
            user.setRoles(List.of(Role.builder().name(request.getRole()).build()));
            user = userRepository.save(user);
            logger.info("New user created successfully");

            return modelMapper.map(user, UserResponse.class);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            throw new AppException("An unexpected error occurred while creating the user.", HttpStatus.BAD_REQUEST);
        }
    }
}
