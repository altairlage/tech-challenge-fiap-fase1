package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.Role;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import com.fiap.GastroHub.shared.config.SecurityConfiguration;
import com.fiap.GastroHub.shared.infra.beans.LogBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateUserUseCase {
    private static final Logger logger = LogManager.getLogger(UpdateUserUseCase.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public UpdateUserUseCase(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @LogBean
    public UserResponse execute(Long id, CreateUpdateUserRequest request) {
        logger.info("Trying to update a user with the following id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        modelMapper.map(request, user);
        user.setPassword(securityConfiguration.passwordEncoder().encode(request.getPassword()));
        user.setRoles(List.of(Role.builder().name(request.getRole()).build()));
        user = userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }
}
