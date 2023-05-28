package com.thelightprojekt.view;

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
import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.view.account.ProfileFragment;
import com.thelightprojekt.viewmodel.UserViewModel;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    private UserViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText emailInput = view.findViewById(R.id.email_login_input);
        TextInputEditText passwordInput = view.findViewById(R.id.password_login_input);
        MaterialButton submitButton = view.findViewById(R.id.login_button);
        MaterialButton registerButton = view.findViewById(R.id.register_button);

        TextView errorMessageLogin = view.findViewById(R.id.error_message_login);
        viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if(error != null){
                    errorMessageLogin.setText(error);
                    errorMessageLogin.setVisibility(View.VISIBLE);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_main_activity,new RegisterFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack("RegisterFragment")
                        .commit();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.login(String.valueOf(emailInput.getText()), String.valueOf(passwordInput.getText()), getViewLifecycleOwner());
                viewModel.getIsLogged().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true){
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.host_fragment_main_activity,new ProfileFragment())
                                    .setReorderingAllowed(true)
                                    .addToBackStack("ProfileFragment")
                                    .commit();
                        }
                    }
                });
            }
        });
    }
}