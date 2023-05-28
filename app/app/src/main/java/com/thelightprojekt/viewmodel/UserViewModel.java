package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.password4j.Password;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.model.data.customer.UserResponse;
import com.thelightprojekt.model.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private UserResponse userLiveData;
    private MutableLiveData<Boolean> isLogged = new MutableLiveData<>();
    private MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        userRepository = new UserRepository();
    }

    public LiveData<CustomerList> getAllCustomers(){
        return userRepository.getAllCustomers();
    }

    public LiveData<CustomerList> getCustomerByEmailFilter(String email) {
        return userRepository.getCustomerByEmailFilter(email);
    }

    public LiveData<String> getErrorMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<Boolean> getIsLogged() {
        return isLogged;
    }

    public void login(String email, String password, LifecycleOwner lifecycleOwner) {
        userRepository.getCustomerByEmailFilter(email).observe(lifecycleOwner, new Observer<CustomerList>() {
            @Override
            public void onChanged(CustomerList customerList) {
                if (customerList != null && customerList.getCustomers() != null && customerList.getCustomers().size() > 0) {
                    String customerId = customerList.getCustomers().get(0).getId();
                    userRepository.getUserById(customerId).observe(lifecycleOwner, new Observer<UserResponse>() {
                        @Override
                        public void onChanged(UserResponse userResponse) {
                            if(userResponse != null){
                                boolean passwordMatches = Password.check(password, userResponse.getUser().getPasswd()).withBcrypt();
                                if (passwordMatches) {
                                    userLiveData = userResponse;
                                    UserState.getInstance().setUser(userResponse.getUser());
                                    System.out.println("L'utilisateur est connecté");
                                    isLogged.setValue(true);
                                } else {
                                    messageLiveData.setValue("Mauvais identifiant");
                                }
                            }
                        }
                    });
                } else {
                    messageLiveData.setValue("Aucun utilisateur trouvé");
                }
            }
        });

    }

    public void register(String firstName, String lastName, String email, String password, String confirmPassword, LifecycleOwner lifecycleOwner){
        if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            messageLiveData.setValue("Please fill all fields");
        }else{
            if(password.equals(confirmPassword)){
                UserResponse user = new UserResponse();
                user.getUser().setFirstname(firstName);
                user.getUser().setLastname(lastName);
                user.getUser().setPasswd(password);
                user.getUser().setEmail(email);
                userRepository.createUser(user).observe(lifecycleOwner, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean userResponse) {
                        if(userResponse == true){
                            login(email, password, lifecycleOwner);
                        }else{
                            messageLiveData.setValue("Error creating account");
                        }
                    }
                });
            }else{
                messageLiveData.setValue("confirmation password does not match");
            }
        }
    }

    public void updateUser(String firstName, String lastName, String email, LifecycleOwner lifecycleOwner){
        String idUser = UserState.getInstance().getUser().getId();
        UserResponse user = new UserResponse();
        user.getUser().setFirstname(firstName);
        user.getUser().setLastname(lastName);
        user.getUser().setEmail(email);
        user.getUser().setId(idUser);
        user.getUser().setPasswd(UserState.getInstance().getUser().getPasswd());
        userRepository.updateUser(user).observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true){
                    UserState.getInstance().setUser(user.getUser());
                    messageLiveData.setValue("User Updated");
                }else {
                    messageLiveData.setValue("Error for updating");
                }
            }
        });
    }

    public void logout(){
        UserState.getInstance().setUser(null);
        isLogged.setValue(false);
    }
}
