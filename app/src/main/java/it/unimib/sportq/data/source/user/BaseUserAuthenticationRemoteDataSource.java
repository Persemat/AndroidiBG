package it.unimib.sportq.data.source.user;

import it.unimib.sportq.data.repository.user.UserResponseCallback;
import it.unimib.sportq.model.User;

public abstract class BaseUserAuthenticationRemoteDataSource {

    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }
    public abstract User getLoggedUser();
    public abstract void logout(User user);
    public abstract void deleteAccount(User user);
    public abstract void signUp(String firstName, String lastName, String email, int id_avatar, String password);
    public abstract void signIn(String email, String password);
}
