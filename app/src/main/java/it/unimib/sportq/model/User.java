package it.unimib.sportq.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    private int image_avatar;

    public User() {
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
