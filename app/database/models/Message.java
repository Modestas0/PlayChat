package database.models;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private LocalDateTime time;
    private String username;
    private String message;

    public Message() {

    }

    public Message(int id, LocalDateTime time, String username, String message) {
        this.id = id;
        this.time = time;
        this.username = username;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
