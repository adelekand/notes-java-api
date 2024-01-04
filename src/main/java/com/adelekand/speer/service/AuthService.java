package com.adelekand.speer.service;

import com.adelekand.speer.dto.request.LoginRequest;
import com.adelekand.speer.dto.request.SignupRequest;
import com.adelekand.speer.dto.response.TokenResponse;
import com.adelekand.speer.enums.ERole;
import com.adelekand.speer.model.User;
import com.adelekand.speer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwt).build();
    }

    public TokenResponse createUser(SignupRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.USER).build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwt).build();
    }
}
