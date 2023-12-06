package it.unimib.sportq.ui.main;

import android.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.sportq.R;
import it.unimib.sportq.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static String TAG = MainActivity.class.getSimpleName();

    BottomNavigationView bottomNavigationView;


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

    }

    private void replaceFragment(androidx.fragment.app.Fragment fragment)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

}
