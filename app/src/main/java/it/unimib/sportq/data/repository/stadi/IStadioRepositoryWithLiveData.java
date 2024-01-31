package it.unimib.sportq.data.repository.stadi;

import androidx.lifecycle.MutableLiveData;

import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.Stadio;
import it.unimib.sportq.model.User;

public interface IStadioRepositoryWithLiveData {
    MutableLiveData<Result> getStadio(String id);
}

