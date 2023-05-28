package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.prescription.PrescriptionList;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;
import com.thelightprojekt.model.interfaces.IPrescriptionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionRepository {
    private IPrescriptionService prescriptionService;

    public PrescriptionRepository() {
        prescriptionService = HttpClientInstance.getRetrofitXml().create(IPrescriptionService.class);
    }

    public LiveData<PrescriptionList> getPrescriptionListByIdCustomer(String id){
        final MutableLiveData<PrescriptionList> data = new MutableLiveData<PrescriptionList>();
        prescriptionService.getPrescriptionListByIdCustomer(id).enqueue(new Callback<PrescriptionList>() {
            @Override
            public void onResponse(Call<PrescriptionList> call, Response<PrescriptionList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<PrescriptionList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<PrescriptionResponse> getPrescriptionById(String id){
        final MutableLiveData<PrescriptionResponse> data = new MutableLiveData<PrescriptionResponse>();
        prescriptionService.getPrescriptionById(id).enqueue(new Callback<PrescriptionResponse>() {
            @Override
            public void onResponse(Call<PrescriptionResponse> call, Response<PrescriptionResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<PrescriptionResponse> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

}
