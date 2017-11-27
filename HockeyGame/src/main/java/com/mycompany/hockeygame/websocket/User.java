/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hockeygame.websocket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author leo_k
 */
public class User {
    private String email;
    private short guessedTeam;
    private short playerOfMatch;
    private int points;

    public User(String email) {
        this.email = email;
        
        try {
            Path path = Paths.get("/hockeyGame/Users/" + email);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                points = Integer.parseInt(lines.get(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getEmail() {
        return email;
    }
    
    public short getGuessedTeam() {
        return guessedTeam;
    }

    public void setGuessedTeam(short guessedTeam) {
        this.guessedTeam = guessedTeam;
    }
    
    public short getPlayerOfMatch() {
        return playerOfMatch;
    }

    public void setPlayerOfMatch(short playerOfMatch) {
        this.playerOfMatch = playerOfMatch;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public void persist() {
        try {
            Path path = Paths.get("/hockeyGame/Users/" + email);
            List<String> lines = Files.readAllLines(path);
            Files.write(path, (lines.get(0) + "\n" + points).getBytes("UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }
}
