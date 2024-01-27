package it.unimib.sportq;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.sportq.model.Prenotazione;
import it.unimib.sportq.model.User;
import it.unimib.sportq.data.repository.AppRepository;

public class AppViewModel extends AndroidViewModel {
    private AppRepository appRepository;

    private final LiveData<List<User>> userList;

    public AppViewModel (Application application) {
        super(application);
        appRepository = new AppRepository(application);
        userList = appRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() { return userList; }

    public LiveData<User> getUserByEmail(String email) {
        return appRepository.getUserByEmail(email);
    }

    public void insertUser(User user) {
        appRepository.insertUser(user);
    }
    public void deleteUser(User user) {
        appRepository.deleteUser(user);
    }

    public void updateUser(User user) {
        appRepository.updateUser(user);
    }

    public void insertPrenotazione(Prenotazione p) {
        appRepository.insertPrenotazione(p);
    }
    public void deletePrenotazione(Prenotazione p) {
        appRepository.deletePrenotazione(p);
    }

    public void updatePrenotazione(Prenotazione p) {
        appRepository.updatePrenotazione(p);
    }

    public void insertUserWithPrenotazioni(User user, List<Prenotazione> p) {
        appRepository.insertUserWithPrenotazioni(user, p);
    }
    public void deleteUserWithPrenotazioni(User user, List<Prenotazione> p) {
        appRepository.deleteUserWithPrenotazioni(user, p);
    }
    public void updateUserWithPrenotazioni(User user, List<Prenotazione> p) {
        appRepository.updateUserWithPrenotazioni(user, p);
    }
}
