package com.statushub.statushub.controller;

import com.statushub.statushub.dto.ChangePasswordRequest;
import com.statushub.statushub.dto.LoginRequest;
import com.statushub.statushub.dto.LoginResponse;
import com.statushub.statushub.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    // Simpel intern User-model (kun til demo)
    private record User(String username, String password, String role) {}

    // Mutable brugertabel i memory (ikke persistent – fint til prototype)
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public AuthController() {
        // initial demo-brugere
        users.put("admin", new User("admin", "admin123", "ADMIN"));
        users.put("viewer", new User("viewer", "viewer123", "VIEWER"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = users.get(request.username());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!user.password().equals(request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String fakeToken = user.username() + "-token";
        LoginResponse resp = new LoginResponse(user.username(), user.role(), fakeToken);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest req) {
        User user = users.get(req.username());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!user.password().equals(req.oldPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
        }

        // opret ny User med samme role, men nyt password
        User updated = new User(user.username(), req.newPassword(), user.role());
        users.put(user.username(), updated);

        return ResponseEntity.ok("Password changed");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        if (req.username() == null || req.username().isBlank()
                || req.password() == null || req.password().isBlank()) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        if (users.containsKey(req.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists");
        }

        String role = req.role();
        if (role == null || role.isBlank()) {
            role = "VIEWER";
        } else {
            role = role.toUpperCase();
            if (!role.equals("ADMIN") && !role.equals("VIEWER")) {
                role = "VIEWER";
            }
        }

        User newUser = new User(req.username(), req.password(), role);
        users.put(newUser.username(), newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created with role: " + role);
    }
}
