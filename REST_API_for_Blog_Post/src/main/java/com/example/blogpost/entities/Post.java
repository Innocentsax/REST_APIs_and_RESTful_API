package com.example.blogpost.entities;

import com.example.blogpost.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    private BlogUser blogUser;
    @ManyToOne
    private Admin admin;
    @OneToMany()
    @JoinColumn
    private List<Comment> comment = new ArrayList<>();
    @OneToMany
    @JoinColumn
    private List<Likes> likes = new ArrayList<>();

}
