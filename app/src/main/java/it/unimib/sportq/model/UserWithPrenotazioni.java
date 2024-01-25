package it.unimib.sportq.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithPrenotazioni {
    @Embedded
    public User user;

    @Relation(parentColumn = "email", entityColumn = "user_email")
    public List<Prenotazione> prenotazioni;
}
