package org.airTribe.taskTrackingSystem.controller;


import org.airTribe.taskTrackingSystem.dto.*;
import org.airTribe.taskTrackingSystem.service.*;
import org.airTribe.taskTrackingSystem.entity.*;
import org.airTribe.taskTrackingSystem.exception.*;

import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserService _userService;

    @Autowired
    private JwtService _jwtService;


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterationDto registerationDto, HttpServletRequest request) {
        User userEntity =  _userService.registerUser(registerationDto);
        String token = UUID.randomUUID().toString();
        String verificationUrl = getApplicationUrl(request) + "auth/verifyRegistration?token=" + token;
        _userService.createVerificationToken(userEntity, token);
        System.out.println("Verification token created for user: " + userEntity.getEmail());
        System.out.println("Verification url: " + verificationUrl);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(_jwtService.getVerifivationExpirationTime());
        Map<String, String> response = new HashMap<>();
        response.put("message", "kindly verify yourself by using verification URL sent to you registered mail: "+registerationDto.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyRegistration")
    public ResponseEntity<Object>  verifyRegistration(@RequestParam String token) throws TokenExpiredException {
        boolean isValid = _userService.validateTokenAndEnableUser(token);
        Map<String, String> response = new HashMap<>();
        if (!isValid)
            response.put("message",  "Invalid token");
        else
        response.put("message",  "User enabled successfully");

        return ResponseEntity.ok(response);
    }


    private String getApplicationUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
        User authenticatedUser = _userService.autheticateUser(loginUserDto);

        String jwtToken = _jwtService.generateToken(authenticatedUser,"login");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setExpiresIn(_jwtService.getLoginExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
