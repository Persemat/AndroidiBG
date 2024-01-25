package it.unimib.sportq.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.sportq.model.Prenotazione;

@Dao
public interface PrenotazioneDao {

    @Query("SELECT * FROM prenotazioni WHERE user_email = :userEmail")
    List<Prenotazione> getPrenotazioneByEmail(String userEmail);

    @Insert
    void insert(Prenotazione prenotazione);

    @Update
    void update(Prenotazione prenotazione);

    @Delete
    void delete(Prenotazione prenotazione);
}
