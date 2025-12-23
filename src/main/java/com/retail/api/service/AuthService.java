package com.retail.api.service;

import com.retail.api.dto.*;
import com.retail.api.entity.AppUser;
import com.retail.api.entity.UserRole;
import com.retail.api.exception.BadRequestException;
import com.retail.api.repository.UserRepository;
import com.retail.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Transactional
  public void register(AuthRegisterRequest req) {
    if (userRepository.existsByUsername(req.getUsername())) {
      throw new BadRequestException("Username already exists: " + req.getUsername());
    }
    AppUser user = AppUser.builder()
        .username(req.getUsername())
        .passwordHash(passwordEncoder.encode(req.getPassword()))
        .role(UserRole.USER)
        .build();
    userRepository.save(user);
  }

  public AuthResponse login(AuthLoginRequest req) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
    );
    AppUser user = userRepository.findByUsername(req.getUsername())
        .orElseThrow(() -> new BadRequestException("User does not exist"));
    String token = jwtService.generateAccessToken(user.getUsername(), user.getRole().name());
    return new AuthResponse(token, "Bearer");
  }
}
