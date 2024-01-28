package it.unimib.sportq.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    private String full_name;
    private String id_token;

    public User() {
    }

    public User(String full_name, @NonNull String email, String id_token) {
        this.email = email;
        this.full_name = full_name;
        this.id_token = id_token;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", full_name='" + full_name + '\'' +
                ", id_token='" + id_token + '\'' +
                '}';
    }
}
