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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import it.unimib.sportq.R;
import it.unimib.sportq.UserViewModel;
import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;
import it.unimib.sportq.ui.welcome.LoginPage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class FragmentAccount extends Fragment {

    private static String TAG = FragmentAccount.class.getSimpleName();
    private Button logoutButton;
    private TextView email;
    private ImageView avatar;

    private TextView first_name;

    private TextView last_name;

    ActivityResultLauncher<Intent> imagePickLauncher;

    Uri selectedImageUri;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    LiveData<User> userLiveData;

    public FragmentAccount() {
        // Required empty public constructor
    }

    //andrebbe in AndroidUtil, qua va bene lo stesso? io non lo so
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if(data != null && data.getData() != null){
                    selectedImageUri = data.getData();
                    setProfilePic(getContext(), selectedImageUri, avatar);
                }
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_account, container, false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        logoutButton = view.findViewById(R.id.logout_button);
        avatar = view.findViewById(R.id.avatar_image);

        if(firebaseUser == null) {
            Log.d(TAG, "Utente Firebase non trovato");
            Intent intent = new Intent(getContext(), LoginPage.class);
            startActivity(intent);
            requireActivity().finish();
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
                    first_name = view.findViewById(R.id.user_firstname);
                    first_name.setText(user.getFirst_name());

                    last_name = view.findViewById(R.id.user_lastname);
                    last_name.setText(user.getLast_name());

                    avatar = view.findViewById(R.id.avatar_image);
                    avatar.setImageResource(user.getImage_avatar());

                    email = view.findViewById(R.id.user_email);
                    email.setText(user.getEmail());
                }
            });
        }

        // GESTIONE DEL CLICK DEL PULSANTE LOGOUT
        logoutButton.setOnClickListener(item -> {
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(getActivity(), LoginPage.class);
            startActivity(login);
        });

        // GESTIONE DEL CLICK DELL'IMMAGINE AVATAR
        avatar.setOnClickListener(item ->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(new Function1<Intent, Unit>() {
                @Override
                public Unit invoke(Intent intent) {
                    imagePickLauncher.launch(intent);
                    return null;
                }
            });
        });

        return view;
    }

}