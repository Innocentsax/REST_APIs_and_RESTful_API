package com.example.blogpost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToOne
    private Post post;
    @ManyToOne
    @JoinColumn
    private BlogUser blogUser;
    @OneToMany
    @JoinColumn
    private List<Likes> likes = new ArrayList<>();
}
