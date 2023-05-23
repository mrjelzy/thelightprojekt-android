package com.thelightprojekt.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.view.account.ProfileFragment;
import com.thelightprojekt.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {

    ProductViewModel productViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.host_fragment_main_activity, HomeFragment.class, null)
                    .commit();
        }

        NavigationBarView navBarr=findViewById(R.id.bottomNavBar);
        LoginFragment loginFragment=new LoginFragment();
        HomeFragment homeFragment=new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        navBarr.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.account:
                        if(UserState.getInstance().getUser() != null){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.host_fragment_main_activity,profileFragment)
                                    .setReorderingAllowed(true)
                                    .addToBackStack("ProfileFragment")
                                    .commit();
                        }else{
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.host_fragment_main_activity,loginFragment)
                                    .setReorderingAllowed(true)
                                    .addToBackStack("LoginFragment")
                                    .commit();
                        }
                        return true;

                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.host_fragment_main_activity,homeFragment)
                                .setReorderingAllowed(true)
                                .addToBackStack("HomeFragment")
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }
}