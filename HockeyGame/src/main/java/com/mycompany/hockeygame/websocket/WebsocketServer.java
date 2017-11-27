/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hockeygame.websocket;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author leo_k
 */
@ApplicationScoped
@ServerEndpoint("/hockeyGame")
public class WebsocketServer {
    private Map<Session, User> clients;
    private String[] teams = new String[2];
    private Map<Short, String> players;
    
    @OnOpen
    public void open(Session session) {
        if(clients == null) clients = new HashMap<>();
        clients.put(session, null);
    }
    
    @OnClose
    public void close(Session session) {
        clients.get(session).persist();
        clients.remove(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {
        Gson gson = new Gson();
        if (message.contains("email:")) {
            clients.put(session, new User(message.replace("email:", "")));
            return;
        }
        
        if ("getTeams".equals(message)) {
            try {
                session.getBasicRemote().sendText("teams;" + gson.toJson(teams));
            } catch (IOException ex) {
                ex.printStackTrace();
                clients.remove(session);
            }
        }
        
        if ("getPlayers".equals(message)) {
            try {
                session.getBasicRemote().sendText("players;" + gson.toJson(players));
            } catch (IOException ex) {
                ex.printStackTrace();
                clients.remove(session);
            }
        }
        
        if ("guessTeam1".equals(message)) {
            clients.get(session).setGuessedTeam((short)1);
            return;
        }
        
        if ("guessTeam2".equals(message)) {
            clients.get(session).setGuessedTeam((short)2);
            return;
        }
        
        if (message.contains("guessPlayer:")) {
            clients.get(session).setPlayerOfMatch(Short.parseShort(message.replace("guessPlayer:", "")));
            return;
        }
        
        if ("scoreTeam1".equals(message)) {
            clients.forEach((s, u) -> {
                if(u != null) {
                    try {
                        if(u.getGuessedTeam() == 1) {
                            s.getBasicRemote().sendText("100;" + teams[0]);
                            u.addPoints(100);
                        } else {
                            s.getBasicRemote().sendText("0;" + teams[1]);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        clients.remove(session);
                    }
                }
            });
            return;
        }
        
        if ("scoreTeam2".equals(message)) {
            clients.forEach((s, u) -> {
                if(u != null) {
                    try {
                        if(u.getGuessedTeam() == 2) {
                            s.getBasicRemote().sendText("100;" + teams[1]);
                            u.addPoints(100);
                        } else {
                            s.getBasicRemote().sendText("0;" + teams[0]);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        clients.remove(session);
                    }
                }
            });
            return;
        }
        
        if (message.contains("finalPlayer:")) {
            clients.forEach((s, u) -> {
                if(u != null) {
                    short player = Short.parseShort(message.replace("finalPlayer:", ""));
                    try {
                        if(u.getPlayerOfMatch() == player) {
                            s.getBasicRemote().sendText("500;" + players.get(player));
                            u.addPoints(500);
                        } else {
                            s.getBasicRemote().sendText("0;" + players.get(player));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        clients.remove(session);
                    }
                }
            });
            return;
        }
        
        if (message.contains("teams")) {
            teams = gson.fromJson(message.replace("teams;", ""), String[].class );
            return;
        }
        
        if (message.contains("playerList")) {
            players = gson.fromJson(message.replace("playerList;", ""), Map.class);
        }
    }
}
