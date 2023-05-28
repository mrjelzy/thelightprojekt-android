package com.thelightprojekt.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.model.data.order.OrderItem;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.prescription.PrescriptionItem;
import com.thelightprojekt.model.data.prescription.PrescriptionList;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.view.adapter.OrderListAdapter;
import com.thelightprojekt.view.adapter.PrescriptionListAdapter;
import com.thelightprojekt.viewmodel.OrderViewModel;
import com.thelightprojekt.viewmodel.PrescriptionViewModel;

import java.util.ArrayList;

public class MyPrescriptionsFragment extends Fragment {


    private PrescriptionViewModel viewModel;
    private ArrayList<PrescriptionResponse> prescriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);
        viewModel.init();
        prescriptions = new ArrayList<PrescriptionResponse>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_prescriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserInfo user = UserState.getInstance().getUser();
        MaterialButton newButton = view.findViewById(R.id.new_prescription_button);



        if(user != null) {

            RecyclerView prescriptionsRecyclerView = view.findViewById(R.id.prescription_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            prescriptionsRecyclerView.setLayoutManager(linearLayoutManager);

            PrescriptionListAdapter adapter = new PrescriptionListAdapter(requireContext(), prescriptions);
            prescriptionsRecyclerView.setAdapter(adapter);
            prescriptionsRecyclerView.setHasFixedSize(true);

            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new NewPrescriptionFragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("NewPrescriptionFragment")
                            .commit();
                }
            });

            viewModel.getPrescriptionListByIdCustomer(user.getId()).observe(getViewLifecycleOwner(), new Observer<PrescriptionList>() {
                @Override
                public void onChanged(PrescriptionList prescriptionList) {
                    if(prescriptionList != null){
                        prescriptions.clear();
                        for(PrescriptionItem p : prescriptionList.getPrescritpions())
                            viewModel.getPrescriptionById(String.valueOf(p.getId())).observe(getViewLifecycleOwner(), new Observer<PrescriptionResponse>() {
                                @Override
                                public void onChanged(PrescriptionResponse prescriptionResponse) {
                                    prescriptions.add(prescriptionResponse);
                                    adapter.setItems(prescriptions);
                                    adapter.notifyDataSetChanged();
                                }

                            });
                    }
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
