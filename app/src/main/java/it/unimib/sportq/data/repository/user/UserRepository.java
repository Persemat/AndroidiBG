package it.unimib.sportq.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.sportq.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.sportq.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.sportq.data.source.user.UserDataRemoteDataSource;
import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.User;

public class UserRepository implements IUserRepository,UserResponseCallback{
    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final MutableLiveData<Result> userMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource, BaseUserDataRemoteDataSource userDataRemoteDataSource){
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        //qui mettere anche quelle delle prenotazioni
    }

    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if (isUserRegistered) {
            signIn(email, password);
        } else {
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    public void saveUserDetails(String userIdToken, String firstName, String lastName) {
        userDataRemoteDataSource.saveUserDetails(userIdToken, firstName, lastName);
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void onSuccessFromAuthentication(User user) {
        if (user != null) {
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {

    }

    @Override
    public void onSuccessFromGettingUserFirstName() {

    }

    @Override
    public void onSuccessFromGettingUserLastName() {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {

    }

    @Override
    public void onSuccessLogout() {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(null);
        userMutableLiveData.postValue(result);
    }
}
