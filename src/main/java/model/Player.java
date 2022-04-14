package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Represents a player
 */
public class Player implements IGameObject {
    private String id;
    //    private String name;
    private final StringProperty nickProperty;
    private String email;
    private String password;
    private List<Game> topGames;

    public String getId() {
        return id;
    }

    public Player(String nick, String email, String password) {
        this.nickProperty = new SimpleStringProperty(nick);
        this.email = email;
        this.password = password;
    }

    public Player(String nick, String email, String password, String id) {
        this.nickProperty = new SimpleStringProperty(nick);
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return nickProperty.getValue();
    }

    public StringProperty getNickProperty() {
        return nickProperty;
    }

    public String getEmail() {
        return email;
    }

    public List<Game> getTopGames() {
        return topGames;
    }

    public void setNick(String nick) {
        this.nickProperty.set(nick);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }
}
