package it.unimib.sportq.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.sportq.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);
}
