package com.fiap.GastroHub.modules.users.infra.http;

import com.fiap.GastroHub.modules.users.dtos.ChangeUserPasswordRequest;
import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.modules.users.usecases.ChangeUserPasswordUseCase;
import com.fiap.GastroHub.modules.users.usecases.CreateUserUseCase;
import com.fiap.GastroHub.modules.users.usecases.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ChangeUserPasswordUseCase changeUserPasswordUseCase;

    public UserController(UserRepository userRepository, CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, ChangeUserPasswordUseCase changeUserPasswordUseCase) {
        this.userRepository = userRepository;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.changeUserPasswordUseCase = changeUserPasswordUseCase;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("/users");
        List<User> users = userRepository.findAll(PageRequest.of(page, size)).getContent();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obter informações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userRepository.findUserById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUpdateUserResponse> createUser(
            @RequestBody CreateUpdateUserRequest createUpdateUserRequest
    ) {
        return new ResponseEntity<>(createUserUseCase.execute(createUpdateUserRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("id") Long id,
            @RequestBody CreateUpdateUserRequest createUpdateUserRequest
    ) {
        logger.info("PUT -> /users/" + id);
        this.updateUserUseCase.execute(id, createUpdateUserRequest);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Void> changeUserPassword(
            @PathVariable("id") Long id,
            @RequestBody ChangeUserPasswordRequest changeUserPasswordRequest
    ) {
        logger.info("PUT -> /users/" + id);
        this.changeUserPasswordUseCase.execute(id, changeUserPasswordRequest);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }

}
