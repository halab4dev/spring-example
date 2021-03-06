package com.github.halab4dev.controller;

import com.github.halab4dev.constant.ResponseCode;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.repository.UserRepository;
import com.github.halab4dev.request.LoginRequest;
import com.github.halab4dev.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 *
 * @author halab
 */
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class ApplicationController {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    @PostMapping("login")
    public String login(
            @RequestBody LoginRequest loginRequest
    ) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (!user.isPresent()) {
            return ResponseCode.INCORRECT_USERNAME_OR_PASSWORD.toString();
        }

        return jwtService.generateJWT(user.get());
    }

    @PreAuthorize("hasAnyAuthority('DELETE_USERS')")
    @GetMapping("super-admin")
    public String deleteUser() {
        return "Hello, super admin";
    }

    @PreAuthorize("hasAnyAuthority('SEARCH_USERS')")
    @GetMapping("admin")
    public String getAll() {
        return "Hello, all admin";
    }

    @PreAuthorize("hasAnyAuthority('GET_USER_DETAIL')")
    @GetMapping("user")
    public String getUserDetail() {
        return "Hello, all admin and user";
    }
}
