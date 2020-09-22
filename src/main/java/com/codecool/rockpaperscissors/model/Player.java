package com.codecool.rockpaperscissors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int wins;

    @Column(nullable = false)
    private int defeats;

    @Column(nullable = false)
    private int draws;

    // roles of the user (ADMIN, USER,..)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @ElementCollection
    private List<String> friends;

}
