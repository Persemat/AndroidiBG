package it.unimib.sportq.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;

    public class UserRepository {

        private UserDao mUserDao;
        private LiveData<List<User>> mAllUsers;

        private ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        // See the BasicSample in the android-architecture-components repository at
        // https://github.com/googlesamples
        public UserRepository(Application application) {
            UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
            mUserDao = db.userDao();
            mAllUsers = mUserDao.getAllUsers();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<User>> getAllUsers() {
            return mAllUsers;
        }

        public LiveData<User> getUserByEmail(String email) {
            return mUserDao.getUserByEmail(email);
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insert(User user) {
            executorService.execute(() -> {
                mUserDao.insertUser(user);
            });
        }
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void delete(User user) {
            executorService.execute(() -> {
                mUserDao.deleteUser(user);
            });
        }
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void update(User user) {
            executorService.execute(() -> {
                mUserDao.updateUser(user);
            });
        }
    }


