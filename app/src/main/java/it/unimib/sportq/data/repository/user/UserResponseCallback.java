package it.unimib.sportq.data.repository.user;

import it.unimib.sportq.model.User;

public interface UserResponseCallback {

    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);

    void onSuccessFromRemoteDatabase(String Name);
    void onSuccessFromGettingUserFirstName();
    void onSuccessFromGettingUserLastName();

    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();

    // gestione callback utenti in locale
}
