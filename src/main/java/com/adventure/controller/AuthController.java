package com.adventure.controller;

import com.adventure.model.Player;
import com.adventure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    // private final UserService userService;
    // private final JwtService jwtService;

    // @PostMapping("/signup")
    // public AuthResponse signup(@RequestBody AuthRequest request) {
    //     log.info("Signup attempt for username: {}", request.getUsername());
    //     if (userService.signup(request.getUsername(), request.getPassword())) {
    //         Player player = Player.createNewPlayer(request.getUsername());
    //         String token = jwtService.generateToken(request.getUsername(), player.getId());
    //         log.info("Signup successful for username: {}", request.getUsername());
    //         return new AuthResponse("Signup successful", token, player);
    //     } else {
    //         log.warn("Signup failed: username already exists: {}", request.getUsername());
    //         return new AuthResponse("Username already exists", null, null);
    //     }
    // }

    // @PostMapping("/login")
    // public AuthResponse login(@RequestBody AuthRequest request) {
    //     log.info("Login attempt for username: {}", request.getUsername());
    //     if (userService.login(request.getUsername(), request.getPassword())) {
    //         Player player = Player.createNewPlayer(request.getUsername());
    //         String token = jwtService.generateToken(request.getUsername(), player.getId());
    //         log.info("Login successful for username: {}", request.getUsername());
    //         return new AuthResponse("Login successful", token, player);
    //     } else {
    //         log.warn("Login failed: invalid credentials for username: {}", request.getUsername());
    //         return new AuthResponse("Invalid credentials", null, null);
    //     }
    // }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String message;
        private String token;
        private Player player;

        public AuthResponse(String message, String token, Player player) {
            this.message = message;
            this.token = token;
            this.player = player;
        }
    }

    // Authentication is now handled by an external OAuth2 provider.
    // This controller can be used for user-related endpoints if needed, but does not handle login/signup or token issuance.
}