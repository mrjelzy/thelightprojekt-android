package com.thelightprojekt.model.interfaces;

import com.thelightprojekt.model.data.address.AddressList;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.address.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAddressService {
    @GET("/api/addresses")
    Call<AddressList> getAddressListByIdCustomer(
            @Query("filter[id_customer]") String id
    );

    @GET("/api/addresses/{id}")
    Call<AddressResponse> getAddressById(
            @Path("id") String id,
            @Query("display") String display
    );

    @GET("/api/countries/{id}")
    Call<CountryResponse> getCountryById(
            @Path("id") String id,
            @Query("display") String display
    );
}
