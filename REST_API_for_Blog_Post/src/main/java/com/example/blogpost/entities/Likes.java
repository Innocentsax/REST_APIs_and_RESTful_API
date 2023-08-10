package com.example.blogpost.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn
    private BlogUser blogUser;
    @ManyToOne
    @JoinColumn
    private Post post;
    @ManyToOne
    @JoinColumn
    private Comment comment;
}
