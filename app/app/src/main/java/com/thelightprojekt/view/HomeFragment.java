package com.thelightprojekt.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thelightprojekt.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button homeFragmentButton = view.findViewById(R.id.home_fragment_button);
        ShopFragment shopFragment = new ShopFragment();
        homeFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_main_activity, ShopFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("DataDisplayFragment") // name can be null
                        .commit();
            }
        });
    }
}