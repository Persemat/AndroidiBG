package it.unimib.sportq.data.source.user;

import android.util.Log;

import it.unimib.sportq.data.repository.user.UserResponseCallback;
import it.unimib.sportq.model.User;

public class UserLocalDataSource extends BaseUserLocalDataSource {

    private static final String TAG = UserLocalDataSource.class.getSimpleName();
    public UserLocalDataSource()
    {

    }

    @Override
    public void saveUserData(User user) {

        //inserimento dati in Room
        Log.d(TAG, "saxses12345rsei dentro");

    }
}
