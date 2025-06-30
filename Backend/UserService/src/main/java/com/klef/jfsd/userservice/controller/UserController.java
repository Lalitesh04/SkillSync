package com.klef.jfsd.userservice.controller;

import com.klef.jfsd.userservice.dto.ProfileRequest;
import com.klef.jfsd.userservice.service.JWTService;
import com.klef.jfsd.userservice.service.UserService;
import jakarta.ws.rs.HeaderParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;




    @PostMapping("create-profile")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    private ResponseEntity<String> createProfile(@RequestBody ProfileRequest request, @HeaderParam("Authorization") String token){
        String email = jwtService.extractUsername(token.substring(7));
        String message = userService.profileCreation(request,email);
        if(message.equals("Profile Created Successfully")){
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.badRequest().body(message);
    }
}

