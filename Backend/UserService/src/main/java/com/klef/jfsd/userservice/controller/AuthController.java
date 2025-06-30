package com.klef.jfsd.userservice.controller;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.dto.UserRegister;
import com.klef.jfsd.userservice.service.JWTService;
import com.klef.jfsd.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;

    public AuthController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
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

    @PostMapping("verification-link")
    public ResponseEntity<String> sendOtp(@RequestParam String email){
        String message = userService.sendVerificationLink(email);
        if (message.equalsIgnoreCase(("please check your email to reset your password."))){
            return ResponseEntity.ok(message);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


    @PostMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token,@RequestBody String password){
        String email =  jwtService.extractUsername(token.substring(7));
        System.out.println(email+" " +password);
        if (email==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        return ResponseEntity.ok(userService.changePassword(email,password));
    }

    @GetMapping("isLogin")
    public ResponseEntity<Boolean> isLogin(@RequestHeader("Authorization") String token){
        String email =  jwtService.extractUsername(token.substring(7));
        boolean isLogin= userService.isLogin(email);
        if(isLogin){
            return ResponseEntity.ok(true);
        }
        return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
    }
}
