package com.codecool.rockpaperscissors.controller;

import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.PlayerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-statistics")
@CrossOrigin("*")
public class StatisticsController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/{username}")
    public String getUserDataBack(@PathVariable("username") String username) throws JSONException {
        Player user = playerRepository.findByUserName(username).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUserName());
        jsonObject.put("wins", user.getWins());
        jsonObject.put("defeats", user.getDefeats());
        jsonObject.put("draws", user.getDraws());
        jsonObject.put("score", user.getScore());
        return jsonObject.toString();
    }

}
