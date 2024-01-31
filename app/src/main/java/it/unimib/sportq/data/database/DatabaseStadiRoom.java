package it.unimib.sportq.data.database;

import static it.unimib.sportq.util.Constants.DATABASE_VERSION;
import static it.unimib.sportq.util.Constants.STADIUM_DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.sportq.model.Stadio;

@Database(entities = {Stadio.class}, version = DATABASE_VERSION)
public abstract class DatabaseStadiRoom extends RoomDatabase {

    public abstract StadioDao stadioDao();

    private static volatile DatabaseStadiRoom INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DatabaseStadiRoom getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseStadiRoom.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseStadiRoom.class, STADIUM_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
