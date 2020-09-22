package com.codecool.rockpaperscissors.controller;

import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/save-my-score")
@CrossOrigin("*")
public class SavePointsController {

    @Autowired
    PlayerRepository playerRepository;


    @PostMapping
    public void saveMyScore(@RequestBody Map<String, Object> data){
        Integer scoreToBeAdded = Integer.parseInt(data.get("score").toString());
        String userName = data.get("userName").toString();
        Player user = playerRepository.findByUserName(userName).get();
        int score = user.getScore();
        score += scoreToBeAdded;
        user.setScore(score);
        if (scoreToBeAdded == 10){
            user.setWins(user.getWins() + 1);
        } else if (scoreToBeAdded == 5){
            user.setDraws(user.getDraws() + 1);
        } else {
            user.setDefeats(user.getDefeats() + 1);
        }
        playerRepository.save(user);
    }
}
