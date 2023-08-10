package com.example.blogpost.dto.response;

import com.example.blogpost.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Cache;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostUpdateResponse {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime modifiedAt;
}
