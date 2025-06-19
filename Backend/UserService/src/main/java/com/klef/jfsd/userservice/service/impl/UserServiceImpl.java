package com.klef.jfsd.userservice.service.impl;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.dto.UserRegister;
import com.klef.jfsd.userservice.models.Role;
import com.klef.jfsd.userservice.models.RoleType;
import com.klef.jfsd.userservice.models.User;
import com.klef.jfsd.userservice.repository.RoleRepository;
import com.klef.jfsd.userservice.repository.UserRepository;
import com.klef.jfsd.userservice.service.JWTService;
import com.klef.jfsd.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String checkLogin(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
        }

        return "Invalid username/password";
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String userRegister(UserRegister userRegister) {

        Role role = null;
        if (userRegister.userType().equalsIgnoreCase("freelancer"))
            role = roleRepository.findByType(RoleType.FREELANCER);
        else if (userRegister.userType().equalsIgnoreCase("client"))
            role = roleRepository.findByType(RoleType.CLIENT);
        else
            return "Invalid Role Type. Please use either freelancer or client";
        if (userRepository.existsByEmail(userRegister.email())){
            return "Email already exists";
        }
        User user = User.builder()
                .email(userRegister.email())
                .password(encoder.encode(userRegister.password()))
                .firstName(userRegister.firstName())
                .lastName(userRegister.lastName())
                .profile(null)
                .role(role)
                .build();
        userRepository.save(user);
        return "User Registered Successfully";
    }

}
