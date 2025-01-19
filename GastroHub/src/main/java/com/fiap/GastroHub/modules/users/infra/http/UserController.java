package com.fiap.GastroHub.modules.users.infra.http;

import com.fiap.GastroHub.modules.users.dtos.*;
import com.fiap.GastroHub.modules.users.usecases.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ChangeUserPasswordUseCase changeUserPasswordUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          ChangeUserPasswordUseCase changeUserPasswordUseCase,
                          GetAllUsersUseCase getAllUsersUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          LoginUserUseCase loginUserUseCase) {

        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.changeUserPasswordUseCase = changeUserPasswordUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUpdateUserRequest request
    ) {
        UserResponse createdUser = createUserUseCase.execute(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("/users");
        List<UserResponse> users = getAllUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        UserResponse userResponse = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("id") Long id,
            @RequestBody CreateUpdateUserRequest request
    ) {
        logger.info("PUT -> /users/{}", id);
        UserResponse updatedUser = updateUserUseCase.execute(id, request);
        return ResponseEntity.ok(updatedUser);
    }

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

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        String token = loginUserUseCase.login(loginUserRequest);
        LoginUserResponse response = new LoginUserResponse(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
