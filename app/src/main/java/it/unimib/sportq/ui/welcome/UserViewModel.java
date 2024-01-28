package it.unimib.sportq.ui.welcome;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.sportq.data.repository.user.IUserRepository;
import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.User;


public class UserViewModel extends ViewModel {

    private static final String TAG = UserViewModel.class.getSimpleName();
    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private boolean authenticationError;


    //COSTRUTTORE
    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
    }



    public MutableLiveData<Result> getUserMutableLiveData(String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }
    public MutableLiveData<Result> getUserMutableLiveData(String firstName, String lastName, String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            getUserData(firstName, lastName, email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }
    public MutableLiveData<Result> getUserMutableLiveData(String idToken) {
        if (userMutableLiveData == null) {
            getUserData(idToken);
        }
        return userMutableLiveData;
    }
    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }
    private void getUserData(String firstName, String lastName, String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(firstName, lastName, email, password, isUserRegistered);
    }
    private void getUserData(String idToken) {
        userMutableLiveData = userRepository.getUserData(idToken);
    }
    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
        }

        return userMutableLiveData;
    }

    public MutableLiveData<Result> deleteAccount() {
        Log.d(TAG, "PENE1VIEWMODEL");
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.deleteAccount();
        } else {
            userRepository.deleteAccount();
        }

        return userMutableLiveData;
    }


    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }


    public boolean isAuthenticationError() {
        return authenticationError;
    }
    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }
}
