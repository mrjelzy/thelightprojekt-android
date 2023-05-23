package com.thelightprojekt.model.interfaces;

import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.model.data.customer.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserService {
    @GET("/api/customers/")
    Call<CustomerList> getAllCustomers();

    @GET("/api/customers")
    Call<CustomerList> getCustomerByEmailFilter(
            @Query("filter[email]") String email
    );

    @GET("/api/customers/{id}")
    Call<UserResponse> getUserById(
            @Path("id") String id,
            @Query("display") String display
    );
}
