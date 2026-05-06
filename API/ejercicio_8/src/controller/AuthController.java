package com.et20.escolar.controller;

import com.et20.escolar.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Autenticación", description = "Login y obtención de token JWT")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Credenciales: { \"username\": \"admin\", \"password\": \"1234\" }"
    )
    public ResponseEntity<?> login(@RequestBody Map<String, String> creds) {
        if ("admin".equals(creds.get("username")) && "1234".equals(creds.get("password"))) {
            return ResponseEntity.ok(Map.of("token", jwtUtil.generarToken("admin")));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Credenciales incorrectas"));
    }
}