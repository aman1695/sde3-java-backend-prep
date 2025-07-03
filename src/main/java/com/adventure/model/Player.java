package com.adventure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String id;
    private String username;
    private int x;
    private int y;
    private int health;
    private String teamId;
    private boolean online;
    private long lastSeen;
    
    public static Player createNewPlayer(String username) {
        return Player.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .x(0)
                .y(0)
                .health(100)
                .online(true)
                .lastSeen(System.currentTimeMillis())
                .build();
    }
} 