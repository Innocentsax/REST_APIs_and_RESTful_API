package com.example.blogpost.dto.response;

import com.example.blogpost.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogUserCommentResponse {
    private Post post;
    private String comment;
    private LocalDateTime createdAt;
}
