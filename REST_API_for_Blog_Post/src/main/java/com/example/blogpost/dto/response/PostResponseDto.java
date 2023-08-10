package com.example.blogpost.dto.response;

import com.example.blogpost.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Category category;
}
