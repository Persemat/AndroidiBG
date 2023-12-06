package it.unimib.sportq;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;
import it.unimib.sportq.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private final LiveData<List<User>> userList;

    public UserViewModel (Application application) {
        super(application);
        userRepository = new UserRepository(application);
        userList = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() { return userList; }

    public LiveData<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
