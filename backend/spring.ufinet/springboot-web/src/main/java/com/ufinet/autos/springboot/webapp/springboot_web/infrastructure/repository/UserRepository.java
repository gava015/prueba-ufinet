package com.ufinet.autos.springboot.webapp.springboot_web.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}

