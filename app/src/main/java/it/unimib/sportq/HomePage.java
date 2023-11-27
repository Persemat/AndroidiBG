package it.unimib.sportq;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    private static String TAG = HomePage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
    }
}
