package com.thelightprojekt.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.viewmodel.UserViewModel;

public class ProfileDetailFragment extends Fragment {
    private UserViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserInfo user = UserState.getInstance().getUser();

        if(user != null){
            TextInputEditText firstNameInput = view.findViewById(R.id.firstname_input_profile);
            TextInputEditText lastNameInput = view.findViewById(R.id.lastname_input_profile);
            TextInputEditText emailInput = view.findViewById(R.id.email_input_profil);
            MaterialButton saveButton = view.findViewById(R.id.save_button_profile);
            MaterialButton returnButton = view.findViewById(R.id.return_button_profile);

            firstNameInput.setText(user.getFirstname());
            lastNameInput.setText(user.getLastname());
            emailInput.setText(user.getEmail());

            TextView messageUpdate = view.findViewById(R.id.message_update);
            viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String message) {
                    if(message != null){
                        messageUpdate.setText(message);
                        messageUpdate.setVisibility(View.VISIBLE);
                    }
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.updateUser(String.valueOf(firstNameInput.getText()),
                            String.valueOf(lastNameInput.getText()),
                            String.valueOf(emailInput.getText()),
                            getViewLifecycleOwner());
                }
            });

            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new ProfileFragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("ProfileFragment")
                            .commit();
                }
            });

        }else{
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.host_fragment_main_activity, new LoginFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("LoginFragment")
                    .commit();
        }

    }
}