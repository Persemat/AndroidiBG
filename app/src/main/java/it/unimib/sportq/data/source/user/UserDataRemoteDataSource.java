package it.unimib.sportq.data.source.user;

import static it.unimib.sportq.util.Constants.FIREBASE_REALTIME_DATABASE;
import static it.unimib.sportq.util.Constants.FIREBASE_USERS_COLLECTION;
import static it.unimib.sportq.util.Constants.FIRST_NAME;
import static it.unimib.sportq.util.Constants.LAST_NAME;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.unimib.sportq.model.User;

public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource{

    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;

    public UserDataRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE);
        databaseReference = firebaseDatabase.getReference().getRef();
    }


    @Override
    public void saveUserData(User user) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getId_token()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "User already present in Firebase Realtime Database");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d(TAG, "User not present in Firebase Realtime Database");
                    databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getId_token()).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage());
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }

    @Override
    public void saveUserDetails(String idToken, String firstName, String lastName) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIRST_NAME).setValue(firstName);
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(LAST_NAME).setValue(lastName);
    }

    @Override
    public void getFirstName(String idToken) {

    }

    @Override
    public void getLastName(String idToken) {

    }
}
