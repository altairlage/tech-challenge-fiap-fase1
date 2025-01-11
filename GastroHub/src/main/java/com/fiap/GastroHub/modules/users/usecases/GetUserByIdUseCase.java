package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetUserByIdUseCase {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetUserByIdUseCase(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.BAD_REQUEST));
        return modelMapper.map(user, UserResponse.class);
    }

}
