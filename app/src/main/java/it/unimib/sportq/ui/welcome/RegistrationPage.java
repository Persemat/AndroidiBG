
package it.unimib.sportq.ui.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;

import it.unimib.sportq.AppViewModel;
import it.unimib.sportq.ui.main.MainActivity;
import it.unimib.sportq.R;
import it.unimib.sportq.model.User;

public class    RegistrationPage extends AppCompatActivity {

    private static String TAG = RegistrationPage.class.getSimpleName();
    private TextInputLayout firstNameTextInputLayout;
    private TextInputLayout lastNameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout newPasswordTextInputLayout;
    private TextInputLayout confirmPasswordTextInputLayout;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inizializzazione di userViewModel
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        firstNameTextInputLayout = findViewById(R.id.text_input_layout_first_name);
        lastNameTextInputLayout = findViewById(R.id.text_input_layout_last_name);
        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        newPasswordTextInputLayout = findViewById(R.id.text_input_layout_new_password);
        confirmPasswordTextInputLayout = findViewById(R.id.text_input_layout_confirm_password);

        // INIZIO GESTIONE PULSANTE DI LOGIN CON GOOGLE
        Button googleLoginButton = findViewById(R.id.button_google_login);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignIn();
            }
        });

        // GESTISCE IL CLICK DEL PULSANTE LOGIN
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(item -> {
            Intent login = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(login);
            finish();
        });

        // GESTISCE IL CLICK DEL PULSANTE SIGN UP
        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String first_name = firstNameTextInputLayout.getEditText().getText().toString();
            String last_name = lastNameTextInputLayout.getEditText().getText().toString();
            String email = emailTextInputLayout.getEditText().getText().toString();
            String new_password = newPasswordTextInputLayout.getEditText().getText().toString();
            String confirm_password = confirmPasswordTextInputLayout.getEditText().getText().toString();
            int idImageAvatar = R.drawable.baseline_account_circle_24;
            // Log.d(TAG, "Id Avatar: " + idImageAvatar);

            if (isNameCorrect(first_name) && isLastNameCorrect(last_name)
                    && isEmailCorrect(email) && isPasswordCorrect(new_password, confirm_password)) {
                mAuth.createUserWithEmailAndPassword(email, confirm_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inserimento dell'utente nel database Room
                                    User user = new User();
                                    user.setEmail(email);
                                    user.setFirst_name(first_name);
                                    user.setLast_name(last_name);
                                    user.setImage_avatar(idImageAvatar);
                                    appViewModel.insertUser(user);

                                    Intent homeIntent = new Intent(RegistrationPage.this, MainActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Snackbar.make(
                                            findViewById(android.R.id.content),
                                            getString(R.string.authentication_failed),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }});
            }
            else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
        // FINE GESTIONE PULSANTE SIGN UP
    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id: ", firebaseUser.getUid());
                            map.put("name: ", firebaseUser.getDisplayName());
                            map.put("profile: ", firebaseUser.getPhotoUrl().toString());

                            database.getReference().child("users").child(firebaseUser.getUid()).setValue(map);

                            // Verifica se l'utente esiste nel database Room
                            appViewModel.getUserByEmail(firebaseUser.getEmail()).observe(RegistrationPage.this, user -> {
                                if (user == null) {
                                    // Se l'utente non esiste nel database Room
                                    // Salva i dati aggiuntivi ottenuti dall'account Google nel database Room
                                    saveGoogleUserDataToRoom(firebaseUser);
                                }
                            });

                            Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegistrationPage.this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveGoogleUserDataToRoom(FirebaseUser user) {
        String fullName = user.getDisplayName();
        String firstName = "", lastName = "";
        String email = user.getEmail();
        if (fullName != null && !fullName.isEmpty()) {
            // Dividi il nome completo in nome e cognome
            String[] parts = fullName.split(" ");

            // Verifica che ci siano almeno due parti (nome e cognome)
            if (parts.length >= 2) {
                firstName = parts[0]; // Prima parte: il nome
                lastName = parts[parts.length-1]; // Ultima parte: il cognome
            }
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirst_name(firstName);
        newUser.setLast_name(lastName);
        newUser.setImage_avatar(R.drawable.baseline_account_circle_24);

        appViewModel.insertUser(newUser);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isNameCorrect(String name) {
        boolean result = name.length()>0;
        if (!result) {
            firstNameTextInputLayout.setError("First name is not correct");
        } else {
            firstNameTextInputLayout.setError(null);
        }
        return result;
    }
    private boolean isLastNameCorrect(String lastName) {
        boolean result = lastName.length()>0;
        if (!result) {
            lastNameTextInputLayout.setError("Last name is not correct");
        } else {
            lastNameTextInputLayout.setError(null);
        }
        return result;
    }
    private boolean isEmailCorrect(String email) {
        boolean result = EmailValidator.getInstance().isValid(email);
        if (!result) {
            emailTextInputLayout.setError("Email is not correct");
        } else {
            emailTextInputLayout.setError(null);
        }
        return result;
    }
    private boolean isPasswordCorrect(String new_password, String confirm_password) {
        boolean result = new_password != null && new_password.length() >= 8;
        if (!result) {
            newPasswordTextInputLayout.setError("Password is not correct");
            confirmPasswordTextInputLayout.setError("");
        } else {
            newPasswordTextInputLayout.setError(null);
        }
        if (!(confirm_password.equals(new_password))) {
            confirmPasswordTextInputLayout.setError("Passwords are not equals");
            result = false;
        } else {
            confirmPasswordTextInputLayout.setError(null);
        }
        return result;
    }
}