package uz.aknb.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.aknb.db.dto.auth.LoginRequest;
import uz.aknb.db.dto.auth.RegisterRequest;
import uz.aknb.app.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
        log.info("Signup request : {}", request);
        authService.signup(request);
        return new ResponseEntity<>("User registration is successful please check email to activate this account.", HttpStatus.OK);
    }

    @GetMapping("/verify-mail/{token}")
    public ResponseEntity<?> verifyMail(@PathVariable("token") String token) {
        log.info("Verify-mail request token: {}", token);
        authService.verifyMail(token);
        return ResponseEntity.ok("User successfully activated");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Login request email: {}", request.getEmail());
        return ResponseEntity.ok(authService.login(request));
    }
}
