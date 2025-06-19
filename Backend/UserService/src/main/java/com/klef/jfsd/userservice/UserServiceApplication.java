
package com.klef.jfsd.userservice;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.models.Role;
import com.klef.jfsd.userservice.models.RoleType;
import com.klef.jfsd.userservice.models.User;
import com.klef.jfsd.userservice.repository.RoleRepository;
import com.klef.jfsd.userservice.repository.UserRepository;
import com.klef.jfsd.userservice.service.JWTService;
import com.klef.jfsd.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner init(RoleRepository repository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (repository.findByName("ROLE_ADMIN") == null) {
                repository.save(Role.builder().name("ROLE_ADMIN").type(RoleType.ADMIN).build());
                repository.save(Role.builder().type(RoleType.FREELANCER).name("ROLE_FREELANCER").build());
                repository.save(Role.builder().type(RoleType.CLIENT).name("ROLE_CLIENT").build());
            }
            if (!userRepository.existsByEmail("admin@gmail.com")) {

                User user = User.builder()
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@gmail.com")
                        .profile(null)
                        .password(encoder.encode("admin@2004"))
                        .role(repository.findByType(RoleType.ADMIN))
                        .build();
                userRepository.save(user);
            }
        };
    }
}