package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.Link;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.data.prescription.PrescriptionList;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;
import com.thelightprojekt.model.repositories.PrescriptionRepository;

public class PrescriptionViewModel extends AndroidViewModel {
    private PrescriptionRepository prescriptionRepository;

    private MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public PrescriptionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        prescriptionRepository = new PrescriptionRepository();
    }

    public LiveData<String> getErrorMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<PrescriptionList> getPrescriptionListByIdCustomer(String id){
        return prescriptionRepository.getPrescriptionListByIdCustomer(id);
    }

    public LiveData<PrescriptionResponse> getPrescriptionById(String id){
        return prescriptionRepository.getPrescriptionById(id);
    }

    public void newPrescription(String odSPH, String odCYL, String odAXIS,
                           String odPD, String ogSPH, String ogCYL, String ogAXIS, String ogPD, LifecycleOwner lifecycleOwner) {

        if (odSPH.isBlank() || odCYL.isBlank() || odAXIS.isBlank() || odPD.isBlank() || ogSPH.isBlank()
                || ogCYL.isBlank() || ogAXIS.isBlank() || ogPD.isBlank()) {
            messageLiveData.setValue("Please fill all fields");
        }else{
            PrescriptionResponse prescription = new PrescriptionResponse();

            prescription.getPrescription().setSphereRight(odSPH);
            prescription.getPrescription().setCylinderRight(odCYL);
            prescription.getPrescription().setAxisRight(odAXIS);
            prescription.getPrescription().setPdRight(odPD);


            prescription.getPrescription().setSphereLeft(ogSPH);
            prescription.getPrescription().setCylinderLeft(ogCYL);
            prescription.getPrescription().setAxisLeft(ogAXIS);
            prescription.getPrescription().setPdLeft(ogPD);

            prescription.getPrescription().setIdCustomer(UserState.getInstance().getUser().getId());


            prescriptionRepository.createPrescription(prescription).observe(lifecycleOwner, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean){
                        messageLiveData.setValue("Prescription Created !");
                    }else{
                        messageLiveData.setValue("Prescription Creation Failed !");
                    }
                }
            });
        }
    }

}
