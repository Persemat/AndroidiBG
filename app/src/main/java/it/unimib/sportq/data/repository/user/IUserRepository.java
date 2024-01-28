package it.unimib.sportq.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.User;

public interface IUserRepository {
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getUser(String firstName, String lastName,String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getUserData(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();
    void signUp(String firstName, String lastName, String email, String password);
    void signIn(String email, String password);
}
