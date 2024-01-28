package it.unimib.sportq.data.source.user;

import it.unimib.sportq.data.repository.user.UserResponseCallback;
import it.unimib.sportq.model.User;

public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;
    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);

    public abstract void saveUserDetails(String idToken, String firstName, String lastName);
    public abstract void getFirstName(String idToken);
    public abstract void getLastName(String idToken);
}
