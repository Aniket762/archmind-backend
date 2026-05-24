package archmind.controller;

import archmind.dto.AuthResponse;
import archmind.dto.LoginRequest;
import archmind.dto.RegisterRequest;
import archmind.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = " Auth API", description = "Authentication Operations" )
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public AuthResponse registerUser(
            @RequestBody RegisterRequest request
    ) {
        return authService.registerUser(request);
    }
    @Operation(summary = "Login user")
    @PostMapping("/login")
    public AuthResponse loginUser(
            @RequestBody LoginRequest request
    ) {
        return authService.loginUser(request);
    }

}
