package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.data.prescription.PrescriptionList;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;
import com.thelightprojekt.model.repositories.PrescriptionRepository;

public class PrescriptionViewModel extends AndroidViewModel {
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        prescriptionRepository = new PrescriptionRepository();
    }

    public LiveData<PrescriptionList> getPrescriptionListByIdCustomer(String id){
        return prescriptionRepository.getPrescriptionListByIdCustomer(id);
    }

    public LiveData<PrescriptionResponse> getPrescriptionById(String id){
        return prescriptionRepository.getPrescriptionById(id);
    }

}
