
package it.unimib.sportq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;

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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
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


        Button loginButton = findViewById(R.id.button_login);
        // GESTISCE IL CLICK DEL PULSANTE LOGIN
        loginButton.setOnClickListener(item -> {
            Intent login = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(login);
            finish();
        });


        Button signUpButton = findViewById(R.id.button_sign_up);
        // GESTISCE IL CLICK DEL PULSANTE SIGN UP
        signUpButton.setOnClickListener(item -> {
            // Log.d(TAG, "Button Clicked");
            String first_name = firstNameTextInputLayout.getEditText().getText().toString();
            String last_name = lastNameTextInputLayout.getEditText().getText().toString();
            String email = emailTextInputLayout.getEditText().getText().toString();
            String new_password = newPasswordTextInputLayout.getEditText().getText().toString();
            String confirm_password = confirmPasswordTextInputLayout.getEditText().getText().toString();

            // TODO: da rivedere le regole della correttezza delle credenziali
            if (isEmailCorrect(email) && isPasswordCorrect(new_password, confirm_password)
                && isNameCorrect(first_name) && isLastNameCorrect(last_name)) {
                mAuth.createUserWithEmailAndPassword(email, confirm_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    /*
                                    Snackbar.make(
                                            findViewById(android.R.id.content),
                                            getString(R.string.authentication_correct),
                                            Snackbar.LENGTH_SHORT).show();
                                    */
                                    UserRoomDatabase userDatabase = UserRoomDatabase.getDatabase(getApplicationContext());
                                    UserDao userDao = userDatabase.userDao();

                                    int idImg = R.drawable.baseline_account_circle_24;

                                    // User nuovoUtente = new User(first_name, last_name, email, idImg);
                                    User user = new User();
                                    user.setEmail(email);
                                    user.setFirst_name(first_name);
                                    user.setLast_name(last_name);
                                    user.setImage_avatar(idImg);

                                    userDao.insertUser(user);

                                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(homeIntent);
                                    finish();
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
            } else {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.error_data),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
        // FINE GESTIONE PULSANTE SIGN UP
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
            if (!(confirm_password.equals(new_password))) {
                confirmPasswordTextInputLayout.setError("Passwords are not equals");
            } else {
                return true;
            }
        }
        return result;
    }
}