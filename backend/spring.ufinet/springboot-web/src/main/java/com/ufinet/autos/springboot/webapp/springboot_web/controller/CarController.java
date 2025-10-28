package com.ufinet.autos.springboot.webapp.springboot_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufinet.autos.springboot.webapp.springboot_web.model.Car;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.CarRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5183")
@RequestMapping("/api/cars") // Todas las rutas empiezan con /api/cars
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE - Crear un auto nuevo
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        // Verificar que el usuario existe
        if (car.getUser() != null && car.getUser().getId() != null) {
            User user = userRepository.findById(car.getUser().getId()).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            car.setUser(user);
        }
        Car savedCar = carRepository.save(car);
        return ResponseEntity.ok(savedCar);
    }

    // READ - Obtener todos los autos
    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // READ - Obtener un auto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    // READ - Obtener autos de un usuario específico
    @GetMapping("/user/{userId}")
    public List<Car> getCarsByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return carRepository.findByUser(user);
        }
        return List.of(); // Lista vacía si no encuentra el usuario
    }

    // UPDATE - Actualizar un auto
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car car = carRepository.findById(id).orElse(null);
        
        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar los campos
        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setYear(carDetails.getYear());
        car.setPlate(carDetails.getPlate());
        car.setColor(carDetails.getColor());

        Car updatedCar = carRepository.save(car);
        return ResponseEntity.ok(updatedCar);
    }

    // DELETE - Eliminar un auto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElse(null);
        
        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        carRepository.delete(car);
        return ResponseEntity.ok("Auto eliminado correctamente");
    }
}