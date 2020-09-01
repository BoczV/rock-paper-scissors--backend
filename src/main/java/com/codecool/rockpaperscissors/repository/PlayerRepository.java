package com.codecool.rockpaperscissors.repository;

import com.codecool.rockpaperscissors.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUserName(String username);
}
