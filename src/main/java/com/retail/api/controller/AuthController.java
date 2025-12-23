package com.retail.api.controller;

import com.retail.api.dto.*;
import com.retail.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@Valid @RequestBody AuthRegisterRequest req) {
    authService.register(req);
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody AuthLoginRequest req) {
    return authService.login(req);
  }
}
