package it.unimib.sportq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.user_email);
        user = auth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }
    }
}
