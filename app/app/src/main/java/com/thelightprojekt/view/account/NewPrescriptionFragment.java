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
import com.thelightprojekt.viewmodel.AddressViewModel;
import com.thelightprojekt.viewmodel.PrescriptionViewModel;


public class NewPrescriptionFragment extends Fragment {

    PrescriptionViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_prescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo user = UserState.getInstance().getUser();
        if(user != null){

            TextView messageUpdate = view.findViewById(R.id.error_message_prescription);
            viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String message) {
                    if(message != null){
                        messageUpdate.setText(message);
                        messageUpdate.setVisibility(View.VISIBLE);
                    }
                }
            });

            TextInputEditText odSPH = view.findViewById(R.id.right_input_sph_edit);
            TextInputEditText odCYL = view.findViewById(R.id.right_input_cyl_edit);
            TextInputEditText odAXIS = view.findViewById(R.id.right_input_axis_edit);
            TextInputEditText odPD = view.findViewById(R.id.right_input_pd_edit);

            TextInputEditText ogSPH = view.findViewById(R.id.left_input_sph_edit);
            TextInputEditText ogCYL = view.findViewById(R.id.left_input_cyl_edit);
            TextInputEditText ogAXIS = view.findViewById(R.id.left_input_axis_edit);
            TextInputEditText ogPD = view.findViewById(R.id.left_input_pd_edit);

            MaterialButton addButton = view.findViewById(R.id.submit_add_prescription_button);


            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.newPrescription(String.valueOf(odSPH.getText()),
                            String.valueOf(odCYL.getText()),
                            String.valueOf(odAXIS.getText()),
                            String.valueOf(odPD.getText()),
                            String.valueOf(ogSPH.getText()),
                            String.valueOf(ogCYL.getText()),
                            String.valueOf(ogAXIS.getText()),
                            String.valueOf(ogPD.getText()),
                            getViewLifecycleOwner());
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