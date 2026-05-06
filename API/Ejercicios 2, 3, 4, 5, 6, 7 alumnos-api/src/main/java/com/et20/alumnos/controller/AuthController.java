// Ejercicio 7

package com.et20.alumnos.controller;

import com.et20.alumnos.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Autenticación", description = "Obtener token de acceso JWT")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * POST /api/login
     * Body: { "username": "admin", "password": "1234" }
     * Retorna: { "token": "eyJ..." }
     *
     * NOTA: En producción las credenciales deben validarse contra una base de datos
     * con contraseñas encriptadas. Este ejemplo usa credenciales hardcodeadas
     * únicamente con fines educativos.
     */
    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión y obtener token JWT",
        description = "Envía usuario y contraseña. Si son correctos, devuelve un token JWT " +
                      "que debe incluirse en el header Authorization: Bearer <token> en las demás llamadas."
    )
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        // Validación simple (reemplazar con lógica real en producción)
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generarToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Usuario o contraseña incorrectos"));
    }
}
