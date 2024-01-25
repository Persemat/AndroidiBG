package it.unimib.sportq.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.sportq.R;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.top_appbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragmentHome, R.id.fragmentSearch, R.id.fragmentAccount,
                R.id.fragmentHistory, R.id.fragmentSport).build();

        // For the Toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}

