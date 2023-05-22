package dev.marvin.shop;

import dev.marvin.shop.appuser.User;
import dev.marvin.shop.appuser.UserRepository;
import dev.marvin.shop.appuser.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class ShopApplication {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {
            userRepository.save(new User("Super", "Admin", "admin@gmail.com", bCryptPasswordEncoder.encode("password"), UserRole.ROLE_ADMIN));
            userRepository.save(new User("John", "Doe", "user@gmail.com", bCryptPasswordEncoder.encode("password"), UserRole.ROLE_USER));

        };
    }
}
