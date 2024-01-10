package it.unimib.sportq.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frase_motivazionale = view.findViewById(R.id.motivational_quotes);
        frase_motivazionale.setText(randomQuotes());
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


