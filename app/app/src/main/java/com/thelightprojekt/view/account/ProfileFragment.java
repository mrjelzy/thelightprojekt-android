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
import com.google.android.material.card.MaterialCardView;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo user = UserState.getInstance().getUser();
        if(user != null) {
            TextView profileName = view.findViewById(R.id.text_profile_name);
            TextView profileEmail = view.findViewById(R.id.text_profile_email);
            MaterialCardView myProfileCard = view.findViewById(R.id.myprofile_card);
            MaterialCardView myOrdersCard = view.findViewById(R.id.myorders_card);
            MaterialCardView myAddressesCard = view.findViewById(R.id.myaddresses_card);
            MaterialButton logoutButton = view.findViewById(R.id.logout_button);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.logout();
                    viewModel.getIsLogged().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean == false) {
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.host_fragment_main_activity, new LoginFragment())
                                        .setReorderingAllowed(true)
                                        .addToBackStack("LoginFragment")
                                        .commit();
                            }
                        }
                    });
                }
            });

            myProfileCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new ProfileDetailFragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("ProfileDetailFragment")
                            .commit();
                }
            });

            myOrdersCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new MyOrdersfragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("MyOrdersFragment")
                            .commit();
                }
            });


            myAddressesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new MyAddressesFragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("MyAddressesFragment")
                            .commit();
                }
            });

            String userFirstAndLastName = user.getFirstname() + " " + user.getLastname();
            profileName.setText(userFirstAndLastName);
            profileEmail.setText(user.getEmail());

        }else{
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.host_fragment_main_activity, new LoginFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("LoginFragment")
                    .commit();
        }
    }
}