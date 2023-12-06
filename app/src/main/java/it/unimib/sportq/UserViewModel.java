package it.unimib.sportq;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.sportq.model.User;
import it.unimib.sportq.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<User>> userList;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        userList = mRepository.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() { return userList; }

    public void insert(User user) { mRepository.insert(user); }
    public void delete(User user) { mRepository.delete(user); }

    public void update(User user) { mRepository.update(user);}
}
