package archmind.service;

import archmind.dto.AuthResponse;
import archmind.dto.LoginRequest;
import archmind.dto.RegisterRequest;
import archmind.model.user.User;
import archmind.model.user.UserRole;
import archmind.model.user.UserStatus;
import archmind.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Update last login
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // 4. Generate token
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, token, user);
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // 2. Build new user
        User user = new User();
        user.setUserName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setEmailValid(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 3. Save to MongoDB
        User saved = userRepository.save(user);

        // 4. Generate token
        String token = jwtService.generateToken(saved.getEmail());

        return new AuthResponse(token, token, saved);
    }
}