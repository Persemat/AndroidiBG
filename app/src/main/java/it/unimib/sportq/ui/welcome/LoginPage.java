package it.unimib.sportq.ui.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Objects;

import it.unimib.sportq.UserViewModel;
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
    FirebaseAuth mAuth;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

        // Inizializzazione di userViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        passwordTextInputLayout = findViewById(R.id.text_input_layout_password);
        signUpButton = findViewById(R.id.button_sign_up);
        loginButton = findViewById(R.id.button_login);
        googleLoginButton = findViewById(R.id.button_google_login);


        // CLICCANDO SU IL PULSANTE DI SIGN UP SI PASSA ALLA SCHERMATA DI SIGN UP
        signUpButton.setOnClickListener(item -> {
            Intent signUp = new Intent(getApplicationContext(), RegistrationPage.class);
            startActivity(signUp);
            finish();
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
                                    userViewModel.getUserByEmail(userEmail).observe(LoginPage.this, user -> {
                                        if (user == null) {
                                            // L'utente non esiste nel database Room, lo aggiungiamo
                                            User newUser = new User();
                                            // Dettagli dell'utente da Firebase
                                            // Inserimento User nel database
                                            userViewModel.insert(newUser);
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