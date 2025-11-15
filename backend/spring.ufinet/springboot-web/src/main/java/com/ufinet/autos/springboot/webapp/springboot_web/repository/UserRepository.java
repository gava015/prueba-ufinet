package com.ufinet.autos.springboot.webapp.springboot_web.repository;

import org.springframework.data.repository.CrudRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}

