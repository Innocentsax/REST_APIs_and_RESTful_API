package com.example.blogpost.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminRequestDto {

    @NotBlank(message = "email is required")
    @Email(message = "provide a valid email")
    private String email;
    @NotBlank(message = "password id required")
    private String password;
}