package uz.aknb.app.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aknb.app.config.MessageConstants;
import uz.aknb.db.dto.auth.AuthenticationResponse;
import uz.aknb.db.dto.auth.LoginRequest;
import uz.aknb.db.dto.auth.RegisterRequest;
import uz.aknb.app.exception.ExceptionHandler;
import uz.aknb.app.exception.ExceptionType;
import uz.aknb.db.mapper.UserMapper;
import uz.aknb.db.entity.EntUser;
import uz.aknb.db.repository.UserRepository;
import uz.aknb.security.service.TokenService;

@Service
public class AuthService implements ExceptionHandler, MessageConstants {

    private final AuthenticationManager authenticationManager;
    private final VerifyTokenService verifyTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final TokenService tokenService;
    private final UserMapper mapper;

    public AuthService(AuthenticationManager authenticationManager,
                       VerifyTokenService verifyTokenService,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       MailService mailService,
                       TokenService tokenService,
                       UserMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.verifyTokenService = verifyTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    @Transactional
    public void signup(RegisterRequest request) {

        String token;
        EntUser user = getEntityByEmail(request.getEmail());
        if (user != null) {

            if (passwordEncoder.matches(request.getPassword(), user.getPassword()) && !user.getVerified()) {
                mapper.update(user, request);
            } else {
                throw exception(ExceptionType.DUPLICATE_ENTITY,EMAIL_NOT_VERIFIED);
            }
        } else {

            user = mapper.toEntity(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(user);
        token = verifyTokenService.create(user);
        mailService.sendVerifyMail(user.getEmail(), token);
    }

    @Transactional
    public EntUser getCurrentUser() {

        Jwt principal = (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return getEntityByEmail(principal.getSubject());
    }

    public void verifyMail(String token) {
        EntUser user = verifyTokenService.isValidToken(token);
        enableUser(user);
    }

    @Transactional
    public void enableUser(EntUser user) {
        user.setVerified(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = tokenService.generateToken(authenticate);
        return new AuthenticationResponse(token, request.getEmail());
    }

    protected EntUser getEntityByEmail(String email) {

        return userRepository.findByEmail(email).orElseGet(() -> null);
    }
}