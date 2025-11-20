package com.ufinet.autos.springboot.webapp.springboot_web.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
}
