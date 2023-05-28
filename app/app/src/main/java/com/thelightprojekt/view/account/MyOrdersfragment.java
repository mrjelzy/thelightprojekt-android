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

import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.model.data.order.OrderItem;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.product.ProductResponse;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.view.adapter.OrderListAdapter;
import com.thelightprojekt.view.adapter.ProductListAdapter;
import com.thelightprojekt.viewmodel.OrderViewModel;
import com.thelightprojekt.viewmodel.UserViewModel;

import org.simpleframework.xml.Order;

import java.util.ArrayList;

public class MyOrdersfragment extends Fragment {

    private OrderViewModel viewModel;
    private ArrayList<OrderResponse> orders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        viewModel.init();
        orders = new ArrayList<OrderResponse>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo user = UserState.getInstance().getUser();


        if(user != null) {

            RecyclerView orderssRecyclerView = view.findViewById(R.id.order_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            orderssRecyclerView.setLayoutManager(linearLayoutManager);

            OrderListAdapter adapter = new OrderListAdapter(requireContext(), orders);
            orderssRecyclerView.setAdapter(adapter);
            orderssRecyclerView.setHasFixedSize(true);

            viewModel.getOrderListByIdCustomer(user.getId()).observe(getViewLifecycleOwner(), new Observer<OrderList>() {
                @Override
                public void onChanged(OrderList orderList) {
                    if(orderList != null){
                        orders.clear();
                        for(OrderItem o : orderList.getOrders())
                            viewModel.getOrderById(String.valueOf(o.getId()), getViewLifecycleOwner()).observe(getViewLifecycleOwner(), new Observer<OrderResponse>() {
                                @Override
                                public void onChanged(OrderResponse orderResponse) {
                                    orders.add(orderResponse);
                                    adapter.setItems(orders);
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