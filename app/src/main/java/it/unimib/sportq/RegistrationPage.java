
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

public class    RegistrationPage extends AppCompatActivity {

    private static String TAG = RegistrationPage.class.getSimpleName();
    private TextInputLayout firstNameTextInputLayout;
    private TextInputLayout lastNameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout newPasswordTextInputLayout;
    private TextInputLayout confirmPasswordTextInputLayout;
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
        setContentView(R.layout.registration_page);
        mAuth = FirebaseAuth.getInstance();

        firstNameTextInputLayout = findViewById(R.id.text_input_layout_first_name);
        lastNameTextInputLayout = findViewById(R.id.text_input_layout_last_name);
        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        newPasswordTextInputLayout = findViewById(R.id.text_input_layout_new_password);
        confirmPasswordTextInputLayout = findViewById(R.id.text_input_layout_confirm_password);

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String first_name = firstNameTextInputLayout.getEditText().getText().toString();
            String last_name = lastNameTextInputLayout.getEditText().getText().toString();
            String email = emailTextInputLayout.getEditText().getText().toString();
            String new_password = newPasswordTextInputLayout.getEditText().getText().toString();
            String confirm_password = confirmPasswordTextInputLayout.getEditText().getText().toString();


            if (isEmailCorrect(email) && isPasswordCorrect(new_password, confirm_password)) {
                Intent homeIntent = new Intent(this, HomePage.class);
                startActivity(homeIntent);
            } else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }

            mAuth.createUserWithEmailAndPassword(email, confirm_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "createUserWithEmail:success");
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.authentication_correct),
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Snackbar.make(
                                        findViewById(android.R.id.content),
                                        getString(R.string.authentication_failed),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(item -> {
            Intent login = new Intent(this, LoginPage.class);
            startActivity(login);
        });
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
            if (!(confirm_password.equals(new_password))) {
                confirmPasswordTextInputLayout.setError("Passwords are not equals");
            } else {
                return true;
            }
        }
        return result;
    }
}