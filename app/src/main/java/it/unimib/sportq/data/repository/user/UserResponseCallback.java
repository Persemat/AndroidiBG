package it.unimib.sportq.data.repository.user;

import it.unimib.sportq.model.User;

public interface UserResponseCallback {

    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);

    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout(User user);

    void onSuccessDeleteUser(String idToken);

    // gestione callback utenti in locale
}
