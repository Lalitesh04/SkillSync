package com.klef.jfsd.userservice.service.impl;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.dto.ProfileRequest;
import com.klef.jfsd.userservice.dto.UserRegister;
import com.klef.jfsd.userservice.models.Profile;
import com.klef.jfsd.userservice.models.Role;
import com.klef.jfsd.userservice.models.RoleType;
import com.klef.jfsd.userservice.models.User;
import com.klef.jfsd.userservice.repository.ProfileRepository;
import com.klef.jfsd.userservice.repository.RoleRepository;
import com.klef.jfsd.userservice.repository.UserRepository;
import com.klef.jfsd.userservice.service.JWTService;
import com.klef.jfsd.userservice.service.MailService;
import com.klef.jfsd.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MailService mailService;

    @Value("${frontend.url}")
    private  String frontendUrl;

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
        if (userRepository.existsByEmail(userRegister.email())) {
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

    @Override
    @Transactional
    public String profileCreation(ProfileRequest profileRequest, String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    Profile profile = Profile.builder()
                            .bio(profileRequest.bio())
                            .location(profileRequest.location())
                            .githubUrl(profileRequest.githubUrl())
                            .profilePictureUrl(profileRequest.profilePictureUrl())
                            .linkedInUrl(profileRequest.linkedInUrl())
                            .portfolioUrl(profileRequest.portfolioUrl())
                            .skills(profileRequest.skills())
                            .user(user)
                            .build();

                    Profile savedProfile = profileRepository.save(profile);

                    user.setProfile(savedProfile);
                    userRepository.save(user);

                    return "Profile Created Successfully";
                })
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


    @Override
    public String changePassword(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    String encodedPassword = encoder.encode(password);
                    user.setPassword(encodedPassword);
                    userRepository.save(user);

                    return "Password Changed Successfully";
                })
                .orElse("User not found with email: ");
    }

    @Override
    public boolean isLogin(String email) {
        return userRepository.isLogin(email);
    }

    @Override
    public String sendVerificationLink(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    String fullName = user.getFirstName() + " " + user.getLastName();
                    String token = jwtService.generateVerificationToken(email);
                    String verificationLink = frontendUrl + "/change-password?token=" + token;
                    System.out.println(verificationLink);
                    return "please check your email to reset your password.";

                    //return mailService.sendMail(email, fullName, verificationLink);
                })
                .orElse("User not found with email: " + email);
    }

}