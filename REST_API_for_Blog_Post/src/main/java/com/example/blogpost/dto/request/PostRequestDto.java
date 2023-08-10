package com.example.blogpost.dto.request;

import com.example.blogpost.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostRequestDto {
    @NotBlank(message = "title is required")
    private String title;
    private Category category;
    @NotBlank(message = "input content")
    private String content;
}
