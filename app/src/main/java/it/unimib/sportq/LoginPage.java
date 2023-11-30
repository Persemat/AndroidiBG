package it.unimib.sportq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

public class LoginPage extends AppCompatActivity {

    private static String TAG = LoginPage.class.getSimpleName();
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();
        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        passwordTextInputLayout = findViewById(R.id.text_input_layout_password);

        // INIZIO GESTIONE PULSANTE DI LOGIN
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String email = emailTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.authentication_correct),
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.authentication_failed),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

            if (isEmailCorrect(email) && isPasswordCorrect(password)) {
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            } else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }
        }); // FINE GESTIONE PULSANTE LOGIN

        // CLICCANDO SU IL PULSANTE DI SIGN UP SI PASSA ALLA SCHERMATA DI SIGN UP
        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(item -> {
            Intent signUp = new Intent(this, RegistrationPage.class);
            startActivity(signUp);
        });
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