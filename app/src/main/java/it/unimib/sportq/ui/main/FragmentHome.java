package it.unimib.sportq.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import it.unimib.sportq.R;

public class FragmentHome extends Fragment {
    private TextView frase_motivazionale;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView historyContainer = view.findViewById(R.id.cardview_storico);

        frase_motivazionale = view.findViewById(R.id.motivational_quotes);
        frase_motivazionale.setText(randomQuotes());

        historyContainer.setOnClickListener(item -> {
            Navigation.findNavController(item).navigate(R.id.action_fragmentHome_to_fragmentHistory);
        });
    }

    public String randomQuotes()
    {
        String frase [] = new String[10];
            frase[0] = "Arrivare secondo significa soltanto essere il primo degli sconfitti.";
            frase[1] = "La forza mentale distingue i campioni dai quasi campioni.";
            frase[2] = "Se non credi in te stesso, troverai sempre un modo per non vincere.";
            frase[3] = "Chi non dà tutto, non dà niente.";
            frase[4] = "Non conta se finisci per terra, conta se ti rialzi.";
            frase[5] = "Se non credi in te stesso, nessuno lo farà per te.";
            frase[6] = "I limiti esistono solo nell’anima di chi è a corto di sogni.";
            frase[7] = "Un vincitore è un sognatore che non si è arreso.";
            frase[8] = "Piu è ardua la sfida, più è grande la vittoria.";
            frase[9] = "Il fallimento non è il contrario del successo, ma una parte del successo.";
        Random random = new Random();
        return frase[random.nextInt(10)];
    }
}


