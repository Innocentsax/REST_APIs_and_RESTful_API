package com.example.blogpost.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogUserSignupRequest {
    @NotBlank(message = "please provide your name")
    private String name;
    @Email(message = "please provide a valid email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "provide your password")
    private  String password;
}
