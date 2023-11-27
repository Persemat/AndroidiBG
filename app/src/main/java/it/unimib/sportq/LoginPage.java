package it.unimib.sportq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

public class LoginPage extends AppCompatActivity {

    private static String TAG = LoginPage.class.getSimpleName();
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        emailTextInputLayout = findViewById(R.id.text_input_layout_email);
        passwordTextInputLayout = findViewById(R.id.text_input_layout_password);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String email = emailTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();

            /*
            Log.d(TAG, "Email: " + email);
            Log.d(TAG, "Password: " + password);

            Log.d(TAG, "Email: " + isEmailCorrect(email));
            Log.d(TAG, "Password: " + isPasswordCorrect(password));
             */

            if (isEmailCorrect(email) && isPasswordCorrect(password)) {
                Intent homeIntent = new Intent(this, HomePage.class);
                startActivity(homeIntent);
            } else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }
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