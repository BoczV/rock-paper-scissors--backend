package com.codecool.rockpaperscissors;

import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class RockPaperScissorsApplication {

    @Autowired
    PlayerRepository userRepository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public static void main(String[] args) {
        SpringApplication.run(RockPaperScissorsApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init(){
        return args -> {
            String encodePassword = passwordEncoder.encode("ugyi");
            Player user = Player.builder().userName("Isti").password(encodePassword).build();
            user.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
            userRepository.save(user);
        };

    }

}
