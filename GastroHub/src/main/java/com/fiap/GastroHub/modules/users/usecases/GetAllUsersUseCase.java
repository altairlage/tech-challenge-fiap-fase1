package com.fiap.GastroHub.modules.users.usecases;

import com.fiap.GastroHub.modules.users.dtos.UserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.shared.AppException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllUsersUseCase {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserResponse> execute() {
        try {
            return userRepository.findAll().stream()
                    .map(user -> modelMapper.map(user, UserResponse.class))
                    .collect(Collectors.toList());
        } catch (Error e) {
            throw new AppException("Error fetching users", HttpStatus.BAD_REQUEST);
        }
    }
}
