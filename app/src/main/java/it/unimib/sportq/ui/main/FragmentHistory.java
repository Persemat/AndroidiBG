package it.unimib.sportq.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.sportq.R;

public class FragmentHistory extends Fragment {

    public FragmentHistory() {
        // Required empty public constructor
    }

    public static FragmentHistory newInstance(String param1, String param2) {
        FragmentHistory fragment = new FragmentHistory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView backButton = view.findViewById(R.id.back_button);

        backButton.setOnClickListener(item -> {
            Navigation.findNavController(item).navigate(R.id.action_fragmentHistory_to_fragmentHome);
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////
        NavController navController = Navigation.findNavController(view);

        // Aggiungi il listener per il cambiamento di destinazione
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Assicurati che il frammento sia ancora associato all'attività
            if (isAdded()) {
                // Ottieni il riferimento alla BottomNavigationView dall'attività
                BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation_view);

                // Imposta manualmente l'elemento "Home" come selezionato quando si è in questo frammento
                MenuItem homeItem = bottomNav.getMenu().findItem(R.id.fragmentHome);
                homeItem.setChecked(destination.getId() == R.id.fragmentHome);
            }
        });
    }
}