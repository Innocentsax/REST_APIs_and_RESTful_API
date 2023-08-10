package com.example.blogpost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogUserLikeResponse {
    private Long postId;
    private Long blogUserId;
    private LocalDateTime likedAt;
}
