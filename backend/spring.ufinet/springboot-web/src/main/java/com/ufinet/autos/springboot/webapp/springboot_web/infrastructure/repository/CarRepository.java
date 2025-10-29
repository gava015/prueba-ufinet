package com.ufinet.autos.springboot.webapp.springboot_web.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.domain.entity.Car;
import com.ufinet.autos.springboot.webapp.springboot_web.domain.entity.User;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    
    // Buscar todos los autos de un usuario específico
    List<Car> findByUser(User user);
    
    // Buscar autos por marca
    List<Car> findByBrand(String brand);
    
    // Buscar auto por placa
    Car findByPlate(String plate);
}
