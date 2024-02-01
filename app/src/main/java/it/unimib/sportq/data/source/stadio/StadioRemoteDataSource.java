package it.unimib.sportq.data.source.stadio;

import static it.unimib.sportq.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.sportq.util.Constants.INVALID_USER_ERROR;
import static it.unimib.sportq.util.Constants.UNEXPECTED_ERROR;
import static it.unimib.sportq.util.Constants.USER_COLLISION_ERROR;
import static it.unimib.sportq.util.Constants.WEAK_PASSWORD_ERROR;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
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

            RectangularBounds lombardyBounds = RectangularBounds.newInstance(
                    new LatLng(45.032989, 8.680265), // Angolo sud-ovest della Lombardia
                    new LatLng(46.385172, 10.496217)// Angolo nord-est della Lombardia
            );

            // Utilizza la session token di autocompletamento
            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

            List<Stadio> stadiumPlaces = new ArrayList<>();

            List<String> queries = Arrays.asList(
                    "arena sportiva",
                    "campo da gioco",
                    "campo di allenamento",
                    "campo sportivo",
                    "centro polisportivo",
                    "centro ricreativo",
                    "centro sportivo",
                    "club sportivo",
                    "complesso sportivo",
                    "impianto sportivo",
                    "installazione sportiva",
                    "luogo sportivo",
                    "parco sportivo",
                    "stadio",
                    "campo da tennis",
                    "campo da calcio",
                    "campo da basket",
                    "campo da baseball",
                    "campo da football",
                    "campo da pallavolo",
                    "pista di atletica"
            );

            for (String query : queries) {
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setSessionToken(token)
                        .setLocationBias(lombardyBounds)
                        .setCountries("IT")
                        .setQuery(query)
                        .build();
                placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                    int predictionsCount = response.getAutocompletePredictions().size();
                    Log.d(TAG, "Number of predictions: " + predictionsCount);
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        String placeId = prediction.getPlaceId();
                        Log.d(TAG, "Place Id: " + placeId);
                        // Effettua una chiamata per ottenere i dettagli completi del luogo utilizzando l'ID del luogo
                        List<Place.Field> placeFields = Arrays.asList(
                                Place.Field.ID,
                                Place.Field.NAME,
                                Place.Field.ADDRESS,
                                Place.Field.LAT_LNG);

                        FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, placeFields);

                        placesClient.fetchPlace(placeRequest).addOnSuccessListener((placeResponse) -> {
                            Place place = placeResponse.getPlace();
                            Log.d(TAG, "Place: " + place.getId());
                            LatLng latLng = place.getLatLng();
                            if (latLng != null) {
                                double latitude = latLng.latitude;
                                double longitude = latLng.longitude;
                                Stadio stadio = new Stadio(
                                        place.getName(),
                                        place.getAddress(),
                                        latitude,
                                        longitude
                                );
                                stadiumPlaces.add(stadio);
                                Log.d(TAG, "Stadio Aggiunto");
                            }
                        }).addOnFailureListener((placeException) -> {
                            if (placeException instanceof ApiException) {
                                ApiException apiException = (ApiException) placeException;
                                Log.e(TAG, "Place details not found: " + apiException.getStatusCode());
                            }
                        });
                    }
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                });
            }
            Log.d(TAG, "List Stadium Size: " + stadiumPlaces.size());
            stadioResponseCallback.onSuccessFromFindingStadiums(stadiumPlaces);
        } else {
            stadioResponseCallback.onFailureFromFindingStadiums("Position permission is not granted");
        }
    }

}

