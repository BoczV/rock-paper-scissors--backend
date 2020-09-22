package com.codecool.rockpaperscissors;

import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.FriendRequestRepository;
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
import java.util.LinkedList;
import java.util.List;

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
            Player user = Player.builder().userName("Isti").password(encodePassword).friends(new LinkedList<>()).build();
            user.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
            user.getFriends().add("Viktor");
            userRepository.save(user);

            String encodePassword2 = passwordEncoder.encode("viktor");
            Player user2 = Player.builder().userName("Viktor").password(encodePassword2).friends(new LinkedList<>()).build();
            user2.setRoles(Arrays.asList("ROLE_USER"));
            user2.getFriends().add("Isti");
            userRepository.save(user2);
        };

    }

}
