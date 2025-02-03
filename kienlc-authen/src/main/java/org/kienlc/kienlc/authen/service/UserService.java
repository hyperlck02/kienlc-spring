package org.kienlc.kienlc.authen.service;

import lombok.RequiredArgsConstructor;
import org.kienlc.kienlc.authen.model.LoginRequest;
import org.kienlc.kienlc.authen.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String verify(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(request);
        }
//        Optional<UserEntity> user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        return "failure";
    }
}
