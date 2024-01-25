package it.unimib.sportq.ui.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import it.unimib.sportq.model.User;
import it.unimib.sportq.ui.main.MainActivity;
import it.unimib.sportq.R;

public class LoginPage extends AppCompatActivity {

    private static String TAG = LoginPage.class.getSimpleName();
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    Button signUpButton;
    Button loginButton;
    Button googleLoginButton;
    Button forgotPasswordButton;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;
    AppViewModel appViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inizializzazione di userViewModel
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        passwordTextInputLayout = findViewById(R.id.text_input_layout_password);
        signUpButton = findViewById(R.id.button_sign_up);
        loginButton = findViewById(R.id.button_login);
        googleLoginButton = findViewById(R.id.button_google_login);
        forgotPasswordButton = findViewById(R.id.button_forgot_password);

        // GESTIONE CLICK PASSWORD DIMENTICATA
        forgotPasswordButton.setOnClickListener(item -> {
            String email = emailTextInputLayout.getEditText().getText().toString();
            if (isEmailCorrect(email)) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });
            }
        });

        // CLICCANDO SU IL PULSANTE DI SIGN UP SI PASSA ALLA SCHERMATA DI SIGN UP
        signUpButton.setOnClickListener(item -> {
            Intent signUp = new Intent(getApplicationContext(), RegistrationPage.class);
            startActivity(signUp);
            finish();
        });

        // INIZIO GESTIONE PULSANTE DI LOGIN CON GOOGLE
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        // INIZIO GESTIONE PULSANTE DI LOGIN
        loginButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String email = emailTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();

            if (isEmailCorrect(email) && isPasswordCorrect(password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if (currentUser != null) {
                                    String userEmail = currentUser.getEmail();

                                    // Verifica se l'utente esiste nel database Room
                                    appViewModel.getUserByEmail(userEmail).observe(LoginPage.this, user -> {
                                        if (user == null) {
                                            // L'utente non esiste nel database Room, lo aggiungiamo
                                            User newUser = new User();
                                            // Dettagli dell'utente da Firebase
                                            // Inserimento User nel database
                                            appViewModel.insertUser(newUser);
                                        }

                                        // Avvia l'Activity principale
                                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.authentication_failed),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
            } else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }
        }); // FINE GESTIONE PULSANTE LOGIN
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
                            appViewModel.getUserByEmail(firebaseUser.getEmail()).observe(LoginPage.this, user -> {
                                if (user == null) {
                                    // Se l'utente non esiste nel database Room
                                    // Salva i dati aggiuntivi ottenuti dall'account Google nel database Room
                                    saveGoogleUserDataToRoom(firebaseUser);
                                }
                            });

                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginPage.this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
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
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    // FUNZIONI PER VERIFICA DELLA CORREZIONE MAIL E PASSWORD
    private boolean isEmailCorrect(String email) {
        boolean result = EmailValidator.getInstance().isValid(email);
        if (!result) {
            emailTextInputLayout.setError("Email is not correct");
        } else {
            emailTextInputLayout.setError(null);
        }
        return result;
    }
    private boolean isPasswordCorrect(String password) {
        boolean result = password != null && password.length() >= 8;
        if (!result) {
            passwordTextInputLayout.setError("Password is not correct");
        } else {
            passwordTextInputLayout.setError(null);
        }
        return result;
    }
}