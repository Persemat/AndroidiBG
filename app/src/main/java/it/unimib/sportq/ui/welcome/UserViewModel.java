package it.unimib.sportq.ui.welcome;


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
    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public void saveUserDetails(String userIdToken, String firstName, String LastName) {
        userRepository.saveUserDetails(userIdToken, firstName, LastName);
    }

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
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
