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
    private String firstName;
    private String lastName;
    private String id_token;

    public User() {
    }

    public User(String firstName,String lastName, @NonNull String email, String id_token) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.id_token=id_token;
    }
    public User(@NonNull String email, String id_token) {
        this.email=email;
        this.id_token=id_token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id_token='" + id_token + '\'' +
                '}';
    }
}
