package com.fiap.GastroHub.modules.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserRequest {
    private long id;
    private String username;
    private String email;
}
