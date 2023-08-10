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
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String password;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<Post> post = new ArrayList<>();


}
