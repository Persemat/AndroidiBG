package it.unimib.sportq.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.sportq.data.repository.stadi.IStadioRepositoryWithLiveData;
import it.unimib.sportq.data.repository.user.IUserRepository;
import it.unimib.sportq.ui.welcome.UserViewModel;

public class StadioViewModelFactory implements ViewModelProvider.Factory {
    private final IStadioRepositoryWithLiveData stadioRepository;

    public StadioViewModelFactory(IStadioRepositoryWithLiveData stadioRepository) {
        this.stadioRepository = stadioRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StadioViewModel(stadioRepository);
    }
}
