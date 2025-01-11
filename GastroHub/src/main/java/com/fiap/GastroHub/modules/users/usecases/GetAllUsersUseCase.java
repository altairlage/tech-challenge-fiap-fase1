package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllUsersUseCase {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GetAllUsersUseCase(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserResponse> execute() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }
}
