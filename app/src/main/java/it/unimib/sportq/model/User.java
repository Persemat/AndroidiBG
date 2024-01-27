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
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    @NonNull
    private int image_avatar;

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

    @NonNull
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(@NonNull String first_name) {
        this.first_name = first_name;
    }

    @NonNull
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NonNull String last_name) {
        this.last_name = last_name;
    }

    public int getImage_avatar() {
        return image_avatar;
    }

    public void setImage_avatar(@NonNull int image_avatar) {
        this.image_avatar = image_avatar;
    }
}
