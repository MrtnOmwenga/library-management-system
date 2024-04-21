package cc.maids.librarymanagement.controller;

import cc.maids.librarymanagement.security.AuthRequest;
import cc.maids.librarymanagement.security.AuthResponse;
import cc.maids.librarymanagement.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        if (authRequest.getUsername().equals(adminUsername) && authRequest.getPassword().equals(adminPassword)) {
            String token = jwtTokenProvider.createToken(adminUsername);
            
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
