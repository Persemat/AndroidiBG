package it.unimib.sportq.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.unimib.sportq.R;
import it.unimib.sportq.databinding.FragmentSportBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSport extends Fragment {
    private TextView nomeSport;
    private ImageView logoSport;

    private ImageView backButton;
    private Button generateMapButton;

    public FragmentSport() {
        // Required empty public constructor
    }

    public static FragmentSport newInstance(String sportName, int sportImage) {
        FragmentSport fragment = new FragmentSport();
        Bundle args = new Bundle();
        args.putString("sportName", sportName);
        args.putInt("sportImage", sportImage);
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nomeSport = view.findViewById(R.id.sport_name);
        logoSport = view.findViewById(R.id.sport_image);

        nomeSport.setText(getArguments().getString("sportName"));
        logoSport.setImageResource(getArguments().getInt("sportImage"));

        backButton = view.findViewById(R.id.back_button);

        backButton.setOnClickListener(item -> {
            Navigation.findNavController(item).navigate(R.id.action_fragmentSport_to_fragmentSearch);
        });

        generateMapButton = view.findViewById(R.id.generate_map_btn);

        generateMapButton.setOnClickListener(item -> {
            Navigation.findNavController(item).navigate(R.id.action_fragmentSport_to_mapsActivityCurrentPlace);
            //Intent intent = new Intent(requireActivity(), MapsActivityCurrentPlace.class);
            //intent.putParcelableArrayListExtra("Stadium_list", new ArrayList<>(stadiumList));
            //startActivity(intent);
        });

    }
}