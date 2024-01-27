package it.unimib.sportq.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.sportq.model.Prenotazione;
import it.unimib.sportq.model.User;

@Database(entities = {User.class, Prenotazione.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract PrenotazioneDao prenotazioneDao();
    public abstract UserWithPrenotazioniDao userWithPrenotazioniDao();

    private static volatile AppRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            /*
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();

            });
            */
        }
    };
    public static AppRoomDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "SportQ_DB").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}
