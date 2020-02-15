package com.github.halab4dev.service;

import com.github.halab4dev.domain.User;
import org.springframework.security.core.Authentication;

/*
 *
 * @author halab
 */
public interface JWTService {

    String generateJWT(User user);

    Authentication getAuthentication(String accessToken);
}
