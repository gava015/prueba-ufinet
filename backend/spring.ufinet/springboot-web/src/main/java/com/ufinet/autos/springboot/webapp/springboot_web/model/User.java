package com.ufinet.autos.springboot.webapp.springboot_web.model;

import java.util.List;

import com.ufinet.autos.springboot.webapp.springboot_web.repository.Token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users") 

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
   

    @Column (unique = true)
    private String username;

    @JsonIgnore
    private String password;


    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;
}
