package it.unimib.sportq.data.source.stadio;

import android.content.Context;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import it.unimib.sportq.BuildConfig;
import it.unimib.sportq.model.Stadio;

public class StadioRemoteDataSource extends BaseStadioRemoteDataSource{

    private static final String TAG = StadioRemoteDataSource.class.getSimpleName();
    private PlacesClient placesClient;

    public StadioRemoteDataSource(Context context) {
        Places.initialize(context, BuildConfig.PLACES_API_KEY);
        placesClient = Places.createClient(context);
    }
    @Override
    public void getStadiumList() {
    }
}

