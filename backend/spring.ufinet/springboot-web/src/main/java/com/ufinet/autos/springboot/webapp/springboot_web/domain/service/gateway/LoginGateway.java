package com.ufinet.autos.springboot.webapp.springboot_web.domain.service.gateway;

import com.ufinet.autos.springboot.webapp.springboot_web.domain.entity.User;
import com.ufinet.autos.springboot.webapp.springboot_web.domain.service.dto.LoginRequest;

public interface LoginGateway {
    User login(LoginRequest request);
}
