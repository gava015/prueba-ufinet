package com.ufinet.autos.springboot.webapp.springboot_web.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int year;
    private String plate;
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
