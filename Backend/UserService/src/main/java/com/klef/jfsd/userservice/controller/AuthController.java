package com.klef.jfsd.userservice.controller;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.dto.UserRegister;
import com.klef.jfsd.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signIn")
    public ResponseEntity<String> checkLogin(@RequestBody LoginRequest loginRequest) {
        String message = userService.checkLogin(loginRequest);
        if ("Invalid username/password".equals(message)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping("/isEmailExist")
    public ResponseEntity<Boolean> isEmailExist(@RequestParam String email){
        return ResponseEntity.ok(userService.isEmailExist(email));
    }
    
    @PostMapping(value = "signUp")
    public ResponseEntity<String> userRegister(@RequestBody UserRegister userRegister){
        String message = userService.userRegister(userRegister);
        if(message.equals("User Registered Successfully")){
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return ResponseEntity.badRequest().body(message);
    }
}
