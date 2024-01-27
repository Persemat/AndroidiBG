package it.unimib.sportq.data.repository.user;

import it.unimib.sportq.model.User;

public interface UserResponseCallback {

    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessLogout();

    void onSuccessFromLocalDatabase(User user);
    void onFailureFromLocalDatabase(String message);

    // gestione callback utenti in locale
}
