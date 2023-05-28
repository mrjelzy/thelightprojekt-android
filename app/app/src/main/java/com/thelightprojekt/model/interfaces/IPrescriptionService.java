package com.thelightprojekt.model.interfaces;

import com.thelightprojekt.model.data.prescription.PrescriptionList;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPrescriptionService {

    @GET("/api/prescriptions")
    Call<PrescriptionList> getPrescriptionListByIdCustomer(
            @Query("filter[id_customer]") String id
    );

    @GET("/api/prescriptions/{id}")
    Call<PrescriptionResponse> getPrescriptionById(
            @Path("id") String id
    );

    @POST("/api/prescriptions")
    Call<PrescriptionResponse> createPrescription(
            @Path("id") String id
    );
}
