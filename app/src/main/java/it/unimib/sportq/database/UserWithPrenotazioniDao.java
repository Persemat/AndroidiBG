package it.unimib.sportq.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import it.unimib.sportq.model.Prenotazione;
import it.unimib.sportq.model.User;
import it.unimib.sportq.model.UserWithPrenotazioni;

@Dao
public interface UserWithPrenotazioniDao {
    @Transaction
    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<UserWithPrenotazioni> getUserWithPrenotazioniByEmail(String email);

    @Insert
    void insert(User user, List<Prenotazione> prenotazioni);

    @Delete
    void delete(User user, List<Prenotazione> prenotazioni);

    @Update
    void update(User user, List<Prenotazione> prenotazioni);
}
