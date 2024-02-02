package it.unimib.sportq.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.sportq.data.repository.stadi.IStadioRepositoryWithLiveData;
import it.unimib.sportq.data.repository.user.IUserRepository;
import it.unimib.sportq.model.Result;
import it.unimib.sportq.ui.welcome.UserViewModel;

public class StadioViewModel extends ViewModel {
    private static final String TAG = StadioViewModel.class.getSimpleName();
    private final IStadioRepositoryWithLiveData stadioRepository;
    private MutableLiveData<Result> stadioMutableLiveData;

    public StadioViewModel(IStadioRepositoryWithLiveData stadioRepository) {
        this.stadioRepository = stadioRepository;
    }

    public MutableLiveData<Result> getStadioMutableLiveData() {
        if (stadioMutableLiveData == null) {
            getListaStadi();
        }
        return stadioMutableLiveData;
    }
    private void getListaStadi() {
        stadioMutableLiveData = stadioRepository.getListaStadi();
    }

}
