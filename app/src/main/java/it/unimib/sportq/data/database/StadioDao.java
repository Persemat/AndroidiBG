package it.unimib.sportq.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.sportq.model.Stadio;

@Dao
public interface StadioDao {

    @Query("SELECT * FROM stadio ORDER BY stadiumName ASC")
    List<Stadio> getAllStadium();

    @Query("SELECT * FROM stadio WHERE id = :id")
    Stadio getStadiumDao(int id);

    @Insert
    void insert(Stadio stadio);

    @Delete
    void delete(Stadio stadio);

    @Update
    void update(Stadio stadio);




}
