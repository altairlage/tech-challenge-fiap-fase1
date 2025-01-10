package com.fiap.GastroHub.modules.users.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeUserPasswordRequest {
    private String currentPassword;
    private String newPassword;
}
