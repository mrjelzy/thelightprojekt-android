package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.thelightprojekt.model.data.address.AddressList;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.address.CountryResponse;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.repositories.AddressRepository;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository addressRepository;

    public AddressViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        addressRepository = new AddressRepository();
    }

    public LiveData<AddressList> getAddressListByIdCustomer(String id){
        return addressRepository.getAddressListByIdCustomer(id);
    }

    public LiveData<AddressResponse> getAddressById(String id, LifecycleOwner lifecycleOwner){
        MutableLiveData<AddressResponse> address = new MutableLiveData<AddressResponse>();
        addressRepository.getAddressById(id).observe(lifecycleOwner, new Observer<AddressResponse>() {
            @Override
            public void onChanged(AddressResponse addressResponse) {
                addressRepository.getCountryById(addressResponse.getAddress().getIdCountry().getValue()).observe(lifecycleOwner, new Observer<CountryResponse>() {
                    @Override
                    public void onChanged(CountryResponse countryResponse) {
                        addressResponse.getAddress().setCountryName(countryResponse.getCountry().getName().getLanguage().getValue());
                        address.setValue(addressResponse);
                    }
                });
            }
        });
        return address;
    }
}
