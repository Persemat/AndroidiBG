package it.unimib.sportq.data.source.user;

import it.unimib.sportq.data.repository.user.UserResponseCallback;
import it.unimib.sportq.model.User;

public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;
    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }
    public abstract void getUserData(String idToken);
    public abstract void saveUserData(User user);
    public abstract void deleteUser(String idToken);
    public abstract void setUserAvatar(String idToken, int selectedImage);

}
