package it.unimib.sportq;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.sportq.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static String TAG = MainActivity.class.getSimpleName();

    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    TextView textView2;
    Button logoutButton;
    BottomNavigationView bottomNavigationView;
    private Fragment homeFragment;
    private Fragment searchFragment;
    private Fragment accountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FragmentHome());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home)
            {
                replaceFragment(new FragmentHome());

            }else if (item.getItemId()== R.id.profile)
            {
                replaceFragment(new FragmentAccount());
            }else if(item.getItemId()==R.id.search)
            {
                replaceFragment(new FragmentSearch());
            }

            return true;
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(item -> {
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(login);
            finish();
        });

*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////7





    }

    private void replaceFragment(androidx.fragment.app.Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}







































        /*
        textView = findViewById(R.id.user_email);
        textView2 = findViewById(R.id.user_name);
        textView.setText(user.getEmail());
         */

        /*
        if (savedInstanceState==null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentAccount.class, null)
                    .commit();
        }
         */