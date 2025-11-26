package com.ufinet.autos.springboot.webapp.springboot_web.repository;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByToken(String jwtToken);

    






}
