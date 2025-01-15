package com.fiap.GastroHub.modules.users.infra.http;

import com.fiap.GastroHub.modules.users.dtos.ChangeUserPasswordRequest;
import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserRequest;
import com.fiap.GastroHub.modules.users.dtos.CreateUpdateUserResponse;
import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import com.fiap.GastroHub.modules.users.infra.orm.repositories.UserRepository;
import com.fiap.GastroHub.modules.users.usecases.ChangeUserPasswordUseCase;
import com.fiap.GastroHub.modules.users.usecases.CreateUserUseCase;
import com.fiap.GastroHub.modules.users.usecases.UpdateUserUseCase;
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
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          ChangeUserPasswordUseCase changeUserPasswordUseCase,
                          GetAllUsersUseCase getAllUsersUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          DeleteUserUseCase deleteUserUseCase) {

        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.changeUserPasswordUseCase = changeUserPasswordUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUpdateUserRequest request
    ) {
        UserResponse createdUser = createUserUseCase.execute(request);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Obter informações de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("/users");
        List<UserResponse> users = getAllUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obter informações de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        UserResponse userResponse = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Atualizar informações de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("id") Long id,
            @RequestBody CreateUpdateUserRequest request
    ) {
        logger.info("PUT -> /users/{}", id);
        UserResponse updatedUser = updateUserUseCase.execute(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Trocar senha de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @PutMapping("/password/{id}")
    public ResponseEntity<Void> changeUserPassword(
            @PathVariable("id") Long id,
            @RequestBody ChangeUserPasswordRequest changeUserPasswordRequest
    ) {
        logger.info("PUT -> password/users/{}", id);
        this.changeUserPasswordUseCase.execute(id, changeUserPasswordRequest);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        logger.info("DELETE -> /users/{}", id);
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
