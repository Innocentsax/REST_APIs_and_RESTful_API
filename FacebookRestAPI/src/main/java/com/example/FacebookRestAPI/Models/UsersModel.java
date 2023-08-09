package com.example.FacebookRestAPI.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users_table")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String login;
    String password;
    String email;
}
