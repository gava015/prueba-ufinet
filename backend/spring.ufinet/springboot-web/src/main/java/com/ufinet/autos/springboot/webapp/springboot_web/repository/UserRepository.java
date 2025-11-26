package com.ufinet.autos.springboot.webapp.springboot_web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    Optional <User> findByUsername(String username);
}

