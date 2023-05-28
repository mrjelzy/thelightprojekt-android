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
import com.thelightprojekt.model.data.address.AddressList;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.address.CountryResponse;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.repositories.AddressRepository;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository addressRepository;
    private MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public AddressViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        addressRepository = new AddressRepository();
    }

    public LiveData<String> getErrorMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<AddressList> getAddressListByIdCustomer(String id) {
        return addressRepository.getAddressListByIdCustomer(id);
    }

    public LiveData<AddressResponse> getAddressById(String id, LifecycleOwner lifecycleOwner) {
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

    public void newAddress(String alias, String firstName, String lastName,
                           String address1, String city, String postcode, String mobilePhone, LifecycleOwner lifecycleOwner) {

        if (firstName.isBlank() || lastName.isBlank() || address1.isBlank() || city.isBlank() || alias.isBlank()
                || postcode.isBlank() || mobilePhone.isBlank()) {
            messageLiveData.setValue("Please fill all fields");
        }else{
            AddressResponse address = new AddressResponse();

            Link idCustomer = new Link(UserState.getInstance().getUser().getId());
            Link idCountry = new Link("1");

            address.getAddress().setAlias(alias);
            address.getAddress().setFirstname(firstName);
            address.getAddress().setLastname(lastName);
            address.getAddress().setAddress1(address1);
            address.getAddress().setCity(city);
            address.getAddress().setPostcode(postcode);
            address.getAddress().setPhoneMobile(mobilePhone);
            address.getAddress().setIdCustomer(idCustomer);
            address.getAddress().setIdCountry(idCountry);
            addressRepository.createAddress(address).observe(lifecycleOwner, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean){
                        messageLiveData.setValue("Address Created !");
                    }else{
                        messageLiveData.setValue("Address Creation Failed !");
                    }
                }
            });
        }
    }
}
