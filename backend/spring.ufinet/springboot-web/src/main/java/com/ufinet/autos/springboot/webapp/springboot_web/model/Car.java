package com.ufinet.autos.springboot.webapp.springboot_web.model;

import jakarta.persistence.*;
import lombok.*;

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
