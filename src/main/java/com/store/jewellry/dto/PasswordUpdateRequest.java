package com.store.jewellry.dto;

import lombok.Data;

@Data
public class PasswordUpdateRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
