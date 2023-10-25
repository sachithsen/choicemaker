package org.sachith.choicemaker.controller;

import org.sachith.choicemaker.model.User;
import org.sachith.choicemaker.model.dto.AuthenticationRequest;
import org.sachith.choicemaker.service.UserInfoService;
/*import org.sachith.choicemaker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private UserInfoService userInfoService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*@Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;*/

    /*@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
        //AuthenticationResponse response = authService.authenticateUser(authRequest);
        AuthenticationResponse response = new AuthenticationResponse("", false);
        if(authRequest.getUsername().equals("user1") || authRequest.getUsername().equals("user2")
                || authRequest.getUsername().equals("user3")){
            response = new AuthenticationResponse("dummy-token", true);
        }
        return ResponseEntity.ok(response);
    }*/


    @GetMapping("/user")
    public User addNewUser(@RequestParam String username) {
        return userInfoService.loadUserByUsername(username);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @PostMapping("/user")
    public User addNewUser(@RequestBody User user) {
        return userInfoService.addUser(user);
    }

    /*@GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                        authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }*/
}
