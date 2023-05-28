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
import com.thelightprojekt.view.account.ProfileFragment;
import com.thelightprojekt.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText lastNameInput = view.findViewById(R.id.lastname_input_register);
        TextInputEditText firstNameInput = view.findViewById(R.id.firstname_input_register);
        TextInputEditText emailInput = view.findViewById(R.id.email_input_register);
        TextInputEditText passwordInput = view.findViewById(R.id.password_input_register);
        TextInputEditText confirmPasswordInput = view.findViewById(R.id.confirm_password_input_register);
        MaterialButton submitButton = view.findViewById(R.id.submit_register_button);


        TextView errorMessageRegister = view.findViewById(R.id.error_message_register);
        viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if(error != null){
                    errorMessageRegister.setText(error);
                    errorMessageRegister.setVisibility(View.VISIBLE);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.register(String.valueOf(firstNameInput.getText()),
                        String.valueOf(lastNameInput.getText()),
                        String.valueOf(emailInput.getText()),
                        String.valueOf(passwordInput.getText()),
                        String.valueOf(confirmPasswordInput.getText()),
                        getViewLifecycleOwner());

                viewModel.getIsLogged().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean != null){
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