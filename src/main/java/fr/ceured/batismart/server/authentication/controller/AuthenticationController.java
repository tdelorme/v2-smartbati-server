package fr.ceured.batismart.server.authentication.controller;

import fr.ceured.batismart.server.authentication.controller.dto.LoginRequest;
import fr.ceured.batismart.server.authentication.controller.dto.LoginResponse;
import fr.ceured.batismart.server.authentication.exception.InvalidTokenException;
import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.commons.ApiResponse;
import fr.ceured.batismart.server.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        ApiResponse<User> response = ApiResponse.<User>builder()
                .data(userService.registerUser(user))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                LoginResponse.builder()
                        .username(request.getEmail())
                        .tokenValid(Boolean.TRUE)
                        .authValid(Boolean.TRUE)
                        .message("User Authenticated using username and password")
                        .jwt(jwtService.generateToken(user.getEmail()))
                        .build()
        );

    }

    @GetMapping("/confirm-email")
    public ResponseEntity<ApiResponse<String>> confirmEmail(@RequestParam("token") String token) throws InvalidTokenException {

        if (userService.verifyUser(token)) {
            return ResponseEntity.ok(ApiResponse.<String>builder().data("Your email has been successfully verified.").build());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
