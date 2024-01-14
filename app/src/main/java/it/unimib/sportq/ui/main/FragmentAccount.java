package it.unimib.sportq.ui.main;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import it.unimib.sportq.R;
import it.unimib.sportq.UserViewModel;
import it.unimib.sportq.adapter.AvatarAdapter;
import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;
import it.unimib.sportq.ui.welcome.LoginPage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class FragmentAccount extends Fragment {

    private static String TAG = FragmentAccount.class.getSimpleName();

    private TextView email;
    private ImageView avatar;
    private TextView first_name;
    private TextView last_name;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    LiveData<User> userLiveData;
    UserViewModel userViewModel;

    public FragmentAccount() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentAccount newInstance(String param1, String param2) {
        FragmentAccount fragment = new FragmentAccount();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Button logoutButton = view.findViewById(R.id.logout_button);
        Button deleteButton = view.findViewById(R.id.delete_account_button);
        first_name = view.findViewById(R.id.user_firstname);
        last_name = view.findViewById(R.id.user_lastname);
        avatar = view.findViewById(R.id.avatar_image);
        email = view.findViewById(R.id.user_email);

        if(firebaseUser == null) {
            Log.d(TAG, "Utente Firebase non trovato");
            Navigation.findNavController(view).navigate(R.id.action_fragmentAccount_to_loginPage);
        } else {
            userLiveData = userViewModel.getUserByEmail(firebaseUser.getEmail());

            userLiveData.observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
                    Log.d(TAG, "Utente Room non trovato");
                    Navigation.findNavController(view).navigate(R.id.action_fragmentAccount_to_loginPage);
                } else {
                    first_name.setText(user.getFirst_name());
                    last_name.setText(user.getLast_name());
                    avatar.setImageResource(user.getImage_avatar());
                    Log.d(TAG, "Id avatar: " + user.getImage_avatar());
                    email.setText(user.getEmail());
                }
            });
        }

        // GESTIONE DEL CLICK DEL PULSANTE LOGOUT
        logoutButton.setOnClickListener(item -> {
            auth.getInstance().signOut();
            signOutFromGoogle();
            Navigation.findNavController(item).navigate(R.id.action_fragmentAccount_to_loginPage);
        });

        // GESTIONE DEL CLICK DEL PULSANTE DELETE ACCOUNT
        deleteButton.setOnClickListener(item -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Elimina l'utente da Firebase Authentication
                user.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted from Firebase.");

                                // Ottenere l'utente dal database Room utilizzando ViewModel
                                userLiveData = userViewModel.getUserByEmail(user.getEmail());
                                userLiveData.observe(getViewLifecycleOwner(), existingUser -> {
                                    if (existingUser != null) {
                                        // Eliminare l'utente dal database Room
                                        userViewModel.delete(existingUser);
                                        Log.d(TAG, "User deleted from Room database.");
                                        signOutFromGoogle();
                                        Navigation.findNavController(item).navigate(R.id.action_fragmentAccount_to_registrationPage);
                                    }
                                });
                            } else {
                                Snackbar.make(
                                        view.findViewById(android.R.id.content),
                                        getString(R.string.error),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        // GESTIONE DEL CLICK DELL'IMMAGINE AVATAR
        avatar.setOnClickListener(v -> showPopup(v));

    } // FINE METODO ONVIEWCREATED()

    private void signOutFromGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        googleSignInClient.signOut()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User signed out from google.");
                        Intent intent = new Intent(getActivity(), LoginPage.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                });
    }
    private void showPopup(View anchorView) {

        // Infla il layout del pop up
        View popupView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.popup_avatar, null);
        // Assegnamento del bottone per chiusura pop up
        Button closeButton = popupView.findViewById(R.id.close_popup);
        // Creazione del pop up
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // Inizializzazione del gridView nel popupView
        GridView gridView = popupView.findViewById(R.id.grid_view_avatar);
        // Creazione e impostazione dell'adapter per il gridView
        AvatarAdapter adapter = new AvatarAdapter(anchorView.getContext());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick() triggered");
                // Ottieni l'ID dell'immagine cliccata
                int selectedImageId = (int) view.getTag();

                userLiveData = userViewModel.getUserByEmail(firebaseUser.getEmail());
                userLiveData.observe(getViewLifecycleOwner(), user -> {
                    if (user == null) {
                        Log.d(TAG, "Utente Room non trovato");
                    } else {
                        // Aggiorna l'avatar dell'utente con l'ID dell'immagine selezionata
                        user.setImage_avatar(selectedImageId);
                        // Log.d(TAG, "Avatar updated");
                        // Aggiorna l'utente nel ViewModel
                        userViewModel.update(user);
                        userLiveData.removeObservers(getViewLifecycleOwner());
                    }
                });
            }
        });

        // animazione di entrata se lo desideri
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // Visualizzazione del popup nella posizione desiderata, ad esempio, al centro dell'ancora
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Gestisci il click del pulsante di chiusura del popup
        closeButton.setOnClickListener(v -> {
            // Esegui le azioni desiderate quando il pulsante di chiusura del popup viene premuto
            popupWindow.dismiss(); // Chiudi il popup
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Rimuovi tutti gli Observer associati a userLiveData
        if (userLiveData != null) {
            userLiveData.removeObservers(getViewLifecycleOwner());
        }
    }
} // FINE CLASSE FRAGMENT ACCOUNT