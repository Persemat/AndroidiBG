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
    private Button logoutButton;
    private Button deleteButton;

    private TextView email;
    private ImageView avatar;
    private TextView first_name;
    private TextView last_name;

    ActivityResultLauncher<Intent> imagePickLauncher;

    Uri selectedImageUri;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    LiveData<User> userLiveData;
    UserViewModel userViewModel;

    public FragmentAccount() {
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
    public static FragmentAccount newInstance(String param1, String param2) {
        FragmentAccount fragment = new FragmentAccount();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if(data != null && data.getData() != null){
                    selectedImageUri = data.getData();
                    setProfilePic(getContext(), selectedImageUri, avatar);
                }
            }

        }); */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_account_button);
        first_name = view.findViewById(R.id.user_firstname);
        last_name = view.findViewById(R.id.user_lastname);
        avatar = view.findViewById(R.id.avatar_image);
        email = view.findViewById(R.id.user_email);

        if(firebaseUser == null) {
            Log.d(TAG, "Utente Firebase non trovato");
            Navigation.findNavController(view).navigate(R.id.action_fragmentAccount_to_loginPage);
            // return view;
        } else {
            //UserRoomDatabase userDatabase = UserRoomDatabase.getDatabase(getContext());
            //UserDao userDao = userDatabase.userDao();

            userLiveData = userViewModel.getUserByEmail(firebaseUser.getEmail());

            userLiveData.observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
                    Log.d(TAG, "Utente Room non trovato");
                    Intent intent = new Intent(getContext(), LoginPage.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    first_name.setText(user.getFirst_name());
                    last_name.setText(user.getLast_name());
                    // Log.d(TAG, "Image_avatar: " + user.getImage_avatar());
                    int selectedImageId = user.getImage_avatar();
                    avatar.setImageResource(selectedImageId);
                    email.setText(user.getEmail());
                }
            });
        }

        // GESTIONE DEL CLICK DEL PULSANTE LOGOUT
        logoutButton.setOnClickListener(item -> {
            FirebaseAuth.getInstance().signOut();
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

        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_avatar, null);
        GridView gridView = popupView.findViewById(R.id.grid_view_avatar);
        AvatarAdapter adapter = new AvatarAdapter(requireContext(), avatar);
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
                        // Aggiorna l'utente nel ViewModel
                        userViewModel.update(user);
                        // Sostituisci l'immagine principale con l'immagine selezionata
                        avatar.setImageResource(selectedImageId);
                        Log.d(TAG, "Immagine User Id: " + user.getImage_avatar());
                    }
                });
            }
        });

        // GESTIONE DEL CLICK DELL'IMMAGINE AVATAR
        avatar.setOnClickListener(v -> showPopup(v));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Rimuovi tutti gli Observer associati a userLiveData
        if (userLiveData != null) {
            userLiveData.removeObservers(getViewLifecycleOwner());
        }
    }

    private void showPopup(View anchorView) {

        Context context = anchorView.getContext();

        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_avatar, null);
        Button closeButton = popupView.findViewById(R.id.close_popup);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Inizializza il GridView nel popupView
        GridView gridView = popupView.findViewById(R.id.grid_view_avatar);
        AvatarAdapter adapter = new AvatarAdapter(requireContext(), avatar);
        gridView.setAdapter(adapter);

        // Gestisci il click del pulsante di chiusura del popup
        closeButton.setOnClickListener(v -> {
            // Esegui le azioni desiderate quando il pulsante di chiusura del popup viene premuto
            popupWindow.dismiss(); // Chiudi il popup
        });

        // animazione di entrata se lo desideri
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        // Visualizzazione del popup nella posizione desiderata, ad esempio, al centro dell'ancora
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }
}