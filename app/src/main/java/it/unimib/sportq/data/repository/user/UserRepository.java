package it.unimib.sportq.data.repository.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import it.unimib.sportq.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.sportq.data.source.user.BaseUserDataRemoteDataSource;
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
        signIn(email, password);
        return userMutableLiveData;
    }
    public MutableLiveData<Result> getUser(String firstName, String lastName, String email, String password, boolean isUserRegistered) {
        signUp(firstName, lastName, email, password);
        return userMutableLiveData;
    }
    public MutableLiveData<Result> getUserData(String idToken){
        userDataRemoteDataSource.getUserData(idToken);
        return userMutableLiveData;
    }
    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }
    @Override
    public MutableLiveData<Result> deleteAccount() {
        Log.d(TAG, "PENE1REPOSITRIIR");
        userRemoteDataSource.deleteAccount();
        return userMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public void signUp(String firstName, String lastName, String email, String password) {
        userRemoteDataSource.signUp(firstName, lastName, email, password);
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
    public void onSuccessFromRemoteDatabase(User user) {Log.d(TAG, "PENE1ONsuccessfromremotedatabaseaseasease");
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        Log.d(TAG, "onSuccessLogout called");
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(new User());
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessDeleteUser(String idToken) {Log.d(TAG, "PENE1successssdeleeeeeeteee");
        userDataRemoteDataSource.deleteUser(idToken);
    }
}