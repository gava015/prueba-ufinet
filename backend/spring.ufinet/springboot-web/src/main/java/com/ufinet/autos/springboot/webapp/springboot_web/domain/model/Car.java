package com.ufinet.autos.springboot.webapp.springboot_web.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String brand;
    private String model;
    private String plate;
    private String color;
    private String year;
}
