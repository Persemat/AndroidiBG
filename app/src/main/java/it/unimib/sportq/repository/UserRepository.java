package it.unimib.sportq.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUser;



}
