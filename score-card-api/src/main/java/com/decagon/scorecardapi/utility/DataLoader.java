package com.decagon.scorecardapi.utility;

import com.decagon.scorecardapi.enums.Gender;
import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.model.SuperAdmin;
import com.decagon.scorecardapi.model.User;
import com.decagon.scorecardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class DataLoader {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.password:password}")
    private String adminPassword;
    @Value("${super.email:email}")
    private String adminEmail;

    @Bean
    public CommandLineRunner preloadAdmin() {
        User user = userRepository.findFirstByRole(Role.SUPER_ADMIN).orElse(null);
        if (user == null) {
            return args -> {
                SuperAdmin user1 = new SuperAdmin("Chika", "Nwobi", Gender.MALE, adminEmail, Role.SUPER_ADMIN, passwordEncoder.encode(adminPassword),true);
                userRepository.save(user1);
            };
        }
        return null;
    }
}