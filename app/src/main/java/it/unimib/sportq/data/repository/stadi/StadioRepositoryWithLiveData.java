package it.unimib.sportq.data.repository.stadi;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.sportq.data.repository.user.UserRepository;
import it.unimib.sportq.data.source.stadio.BaseStadioLocalDataSource;
import it.unimib.sportq.data.source.stadio.BaseStadioRemoteDataSource;
import it.unimib.sportq.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.sportq.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.Stadio;
import it.unimib.sportq.model.StadioApiResponse;

public class StadioRepositoryWithLiveData implements IStadioRepositoryWithLiveData, StadioResponseCallback {
    private static final String TAG = StadioRepositoryWithLiveData.class.getSimpleName();

    private final BaseStadioRemoteDataSource stadioRemoteDataSource;
    private final BaseStadioLocalDataSource stadioLocalDataSource;
    private final MutableLiveData<Result> stadioMutableLiveData;

    public StadioRepositoryWithLiveData(BaseStadioRemoteDataSource stadioRemoteDataSource, BaseStadioLocalDataSource stadioLocalDataSource){
        this.stadioRemoteDataSource = stadioRemoteDataSource;
        this.stadioLocalDataSource = stadioLocalDataSource;
        this.stadioMutableLiveData = new MutableLiveData<>();
        this.stadioRemoteDataSource.setStadioResponseCallback(this);
        this.stadioLocalDataSource.setStadioResponseCallback(this);
    }

    @Override
    public MutableLiveData<Result> getStadio(String id) {
        return null;
    }

    @Override
    public void onSuccessFromFindingStadiums(List<Stadio> stadioList) {

    }

    @Override
    public void onFailureFromFindingStadiums(String message) {

    }
}
