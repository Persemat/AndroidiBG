package it.unimib.sportq.data.source.stadio;

import static it.unimib.sportq.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.sportq.util.Constants.INVALID_USER_ERROR;
import static it.unimib.sportq.util.Constants.UNEXPECTED_ERROR;
import static it.unimib.sportq.util.Constants.USER_COLLISION_ERROR;
import static it.unimib.sportq.util.Constants.WEAK_PASSWORD_ERROR;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unimib.sportq.BuildConfig;
import it.unimib.sportq.model.Stadio;
import it.unimib.sportq.ui.main.MapsActivityCurrentPlace;
import it.unimib.sportq.util.PermissionManager;

public class StadioRemoteDataSource extends BaseStadioRemoteDataSource{

    private static final String TAG = StadioRemoteDataSource.class.getSimpleName();
    private PlacesClient placesClient;
    private PermissionManager permissionManager;

    public StadioRemoteDataSource(Context context) {
        Places.initialize(context, BuildConfig.PLACES_API_KEY);
        placesClient = Places.createClient(context);
        permissionManager = PermissionManager.getInstance();
    }

    @Override
    public void getListaStadi() {
        if (permissionManager.isLocationPermissionGranted()) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);

            // Use the builder to create a FindCurrentPlaceRequest.
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);

            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
                    placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener (new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        FindCurrentPlaceResponse response = task.getResult();
                        // Estrai i luoghi dalla risposta
                        List<PlaceLikelihood> placeLikelihoods = response.getPlaceLikelihoods();
                        List<Stadio> stadiumPlaces = new ArrayList<>();

                        // Filtra i luoghi per trovare quelli che potrebbero essere stadi sportivi
                        for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                            Place place = placeLikelihood.getPlace();
                            List<String> placeTypes = place.getPlaceTypes();
                            if (placeTypes != null) {
                                LatLng latLng = place.getLatLng();
                                double latitude = latLng.latitude;
                                double longitude = latLng.longitude;
                                Stadio stadio = new Stadio(
                                        place.getName(),
                                        place.getAddress(),
                                        latitude,
                                        longitude
                                );
                                stadiumPlaces.add(stadio);
                            }
                        }
                        stadioResponseCallback.onSuccessFromFindingStadiums(stadiumPlaces);
                    }
                    else {
                        stadioResponseCallback.onFailureFromFindingStadiums("Could not find stadiums");
                    }
                }
            });
        } else {
            stadioResponseCallback.onFailureFromFindingStadiums("Position permission is not granted");
        }
    }

}

