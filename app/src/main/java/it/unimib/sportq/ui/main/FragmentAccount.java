package it.unimib.sportq.ui.main;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import it.unimib.sportq.R;
import it.unimib.sportq.database.UserDao;
import it.unimib.sportq.database.UserRoomDatabase;
import it.unimib.sportq.model.User;
import it.unimib.sportq.ui.welcome.LoginPage;


public class FragmentAccount extends Fragment {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK)
            {
                Intent data = result.getData();
                if(data != null && data.getData() != null)
                {
                    selectedImageUri = data.getData();
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
        if(firebaseUser == null) {
            Intent intent = new Intent(getContext(), LoginPage.class);
            startActivity(intent);
            // requireActivity().finish();
            // return view;
        } else {
            UserRoomDatabase userDatabase = UserRoomDatabase.getDatabase(getContext());
            UserDao userDao = userDatabase.userDao();

            userLiveData = userDao.getUserByEmail(firebaseUser.getEmail());

            userLiveData.observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
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
            logoutButton = view.findViewById(R.id.logout_button);
            logoutButton.setOnClickListener(item -> {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getActivity(), LoginPage.class);
                startActivity(login);
            });

            /* TODO: metodo per modificare avatar profilo
            avatar.setOnClickListener(item ->{

                });
            */
        }
        ////////////////////////////////////////////////////////////////////////////////////

        //avatar.setOnClickListener(item ->{
        //});
        return view;
    }

}