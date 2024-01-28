package it.unimib.sportq.ui.main;

import static it.unimib.sportq.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.sportq.util.Constants.INVALID_USER_ERROR;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.sportq.AppViewModel;
import it.unimib.sportq.R;
import it.unimib.sportq.adapter.AvatarAdapter;
import it.unimib.sportq.data.repository.user.IUserRepository;
import it.unimib.sportq.model.Result;
import it.unimib.sportq.model.User;
import it.unimib.sportq.ui.welcome.LoginPage;
import it.unimib.sportq.ui.welcome.UserViewModel;
import it.unimib.sportq.ui.welcome.UserViewModelFactory;
import it.unimib.sportq.util.ServiceLocator;

public class FragmentAccount extends Fragment {

    private static String TAG = FragmentAccount.class.getSimpleName();

    private TextView email;
    private ImageView avatar;
    private TextView first_name;
    private TextView last_name;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    AppViewModel appViewModel;

    private UserViewModel userViewModel;

    public FragmentAccount() {
        // Required empty public constructor
    }

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

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        Button logoutButton = view.findViewById(R.id.logout_button);
        Button deleteButton = view.findViewById(R.id.delete_account_button);

        first_name = view.findViewById(R.id.user_firstname);
        last_name = view.findViewById(R.id.user_lastname);
        avatar = view.findViewById(R.id.avatar_image);
        email = view.findViewById(R.id.user_email);

        User userLogged = userViewModel.getLoggedUser();
        Log.d(TAG, userLogged.toString());



        if(userLogged!=null)
        {
            userViewModel.getUserMutableLiveData(userLogged.getId_token()).observe(
                    getViewLifecycleOwner(), result -> {
                        if (result.isSuccess()) {
                            User user = ((Result.UserResponseSuccess) result).getData();
                            first_name.setText(user.getFirstName());
                            last_name.setText(user.getLastName());
                            email.setText(user.getEmail());
                            //avatar.setImageResource(user.ge());
                        } else {
                            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                    getErrorMessage(((Result.Error) result).getMessage()),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
        // GESTIONE DEL CLICK DEL PULSANTE LOGOUT
        logoutButton.setOnClickListener(v -> {
            userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Navigation.findNavController(view).navigate(
                            R.id.action_fragmentAccount_to_loginPage);
                } else {
                    Snackbar.make(view,
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        });

        /*
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
                                userLiveData = appViewModel.getUserByEmail(user.getEmail());
                                userLiveData.observe(getViewLifecycleOwner(), existingUser -> {
                                    if (existingUser != null) {
                                        // Eliminare l'utente dal database Room
                                        appViewModel.deleteUser(existingUser);
                                        Log.d(TAG, "User deleted from Room database.");
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
         */
        // GESTIONE DEL CLICK DELL'IMMAGINE AVATAR
        // avatar.setOnClickListener(v -> showPopup(v));

    } // FINE METODO ONVIEWCREATED()
    private String getErrorMessage(String errorType) {
        switch (errorType) {
            default:
                return getString(R.string.unexpected_error);
        }
    }
    /*
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

                userLiveData = appViewModel.getUserByEmail(firebaseUser.getEmail());
                userLiveData.observe(getViewLifecycleOwner(), user -> {
                    if (user == null) {
                        Log.d(TAG, "Utente Room non trovato");
                    } else {
                        // Aggiorna l'avatar dell'utente con l'ID dell'immagine selezionata
                        user.setImage_avatar(selectedImageId);
                        // Log.d(TAG, "Avatar updated");
                        // Aggiorna l'utente nel ViewModel
                        appViewModel.updateUser(user);
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
*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}