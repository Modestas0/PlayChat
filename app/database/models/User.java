package database.models;

import java.time.LocalDateTime;

public class User {
    private String username;
    private String passwordHash;
    private LocalDateTime lastActive;

    public User() {

    }

    public User(String username, String passwordHash, LocalDateTime lastActive) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.lastActive = lastActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
}
