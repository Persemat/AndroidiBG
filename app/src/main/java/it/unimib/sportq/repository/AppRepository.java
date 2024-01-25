package it.unimib.sportq.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.sportq.database.AppRoomDatabase;
import it.unimib.sportq.database.PrenotazioneDao;
import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserWithPrenotazioniDao;
import it.unimib.sportq.model.Prenotazione;
import it.unimib.sportq.model.User;
import it.unimib.sportq.model.UserWithPrenotazioni;

public class AppRepository {
    private UserDao mUserDao;
    private PrenotazioneDao mPrenotazioneDao;
    private UserWithPrenotazioniDao mUserWithPrenotazioniDao;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public AppRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getAppDatabase(application);
        mUserDao = db.userDao();
        mPrenotazioneDao = db.prenotazioneDao();
        mUserWithPrenotazioniDao = db.userWithPrenotazioniDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return mUserDao.getAllUsers();
    }

    public LiveData<User> getUserByEmail(String email) {
        return mUserDao.getUserByEmail(email);
    }

    public void insertUser(User user) {
        executorService.execute(() -> {
            mUserDao.insert(user);
        });
    }
    public void deleteUser(User user) {
        executorService.execute(() -> {
            mUserDao.delete(user);
        });
    }
    public void updateUser(User user) {
        executorService.execute(() -> {
            mUserDao.update(user);
        });
    }

    public List<Prenotazione> getPrenotazioneByEmail(String userEmail) {
        return mPrenotazioneDao.getPrenotazioneByEmail(userEmail);
    }

    public void insertPrenotazione(Prenotazione p) {
        executorService.execute(() -> {
            mPrenotazioneDao.insert(p);
        });
    }
    public void deletePrenotazione(Prenotazione p) {
        executorService.execute(() -> {
            mPrenotazioneDao.delete(p);
        });
    }
    public void updatePrenotazione(Prenotazione p) {
        executorService.execute(() -> {
            mPrenotazioneDao.update(p);
        });
    }

    public void insertUserWithPrenotazioni(User user, List<Prenotazione> p) {
        executorService.execute(() -> {
            mUserWithPrenotazioniDao.insert(user, p);
        });
    }
    public void deleteUserWithPrenotazioni(User user, List<Prenotazione> p) {
        executorService.execute(() -> {
            mUserWithPrenotazioniDao.delete(user, p);
        });
    }
    public void updateUserWithPrenotazioni(User user, List<Prenotazione> p) {
        executorService.execute(() -> {
            mUserWithPrenotazioniDao.update(user, p);
        });
    }

}
