package it.unimib.sportq.data.source.user;

import it.unimib.sportq.data.repository.user.UserResponseCallback;
import it.unimib.sportq.model.User;

public abstract class BaseUserLocalDataSource {
    protected UserResponseCallback userResponseCallback;
    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);

    //bisogna mettere altri metodi per gestire prenotazioni utente
}
