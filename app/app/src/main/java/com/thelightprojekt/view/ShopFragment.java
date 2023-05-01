package com.thelightprojekt.view;

import android.graphics.Bitmap;
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
import com.thelightprojekt.model.data.ProductList;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.view.adapter.ProductListAdapter;
import com.thelightprojekt.viewmodel.ProductViewModel;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private ProductViewModel viewModel;
    private ArrayList<ProductResponse> products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.init();
        products = new ArrayList<ProductResponse>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        RecyclerView productsRecyclerView = view.findViewById(R.id.product_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productsRecyclerView.setLayoutManager(linearLayoutManager);

        ProductListAdapter adapter = new ProductListAdapter(requireContext(), products);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.setHasFixedSize(true);

        viewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<ProductList>() {
            @Override
            public void onChanged(ProductList productList) {
                if(productList != null){
                    int nbProducts = productList.getProducts().size();
                    for(int i=1; i<nbProducts; i++){
                        int finalI = i;
                        viewModel.getProductResponseLiveData(i).observe(getViewLifecycleOwner(), new Observer<ProductResponse>() {
                            @Override
                            public void onChanged(ProductResponse productResponse) {
                                if(productResponse != null){
                                    products.add(productResponse);
                                    adapter.setItems(products);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });



    }
}