package model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "uid", unique = true)
    private String UID;

    @Column(name = "chat_id")
    private long chatID;

    public User(String username, long chatId) {
        this.UID = username;
        this.chatID = chatId;
    }

    public User() {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }
}
