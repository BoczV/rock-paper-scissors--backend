package com.codecool.rockpaperscissors.controller;

import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.PlayerRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/high-scores")
@CrossOrigin("*")
public class HighScoresController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    public String returnUsers() throws JSONException {
        List<Player> users = playerRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for (Player user : users) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUserName());
            jsonObject.put("score", user.getScore());
            jsonObject.put("roles", user.getRoles());
            jsonObject.put("wins", user.getWins());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
