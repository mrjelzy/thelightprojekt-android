package com.thelightprojekt.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thelightprojekt.R;
import com.thelightprojekt.model.data.product.ProductList;
import com.thelightprojekt.model.data.product.ProductResponse;
import com.thelightprojekt.model.data.SubSimpleAssociation;
import com.thelightprojekt.view.adapter.ProductListAdapter;
import com.thelightprojekt.view.product.ProductFragment;
import com.thelightprojekt.viewmodel.ProductViewModel;

import java.util.ArrayList;

public class ShopFragment extends Fragment implements ProductListAdapter.OnItemClickListener {

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
        adapter.setOnItemClickListener(this);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.setHasFixedSize(true);

        viewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<ProductList>() {
            @Override
            public void onChanged(ProductList productList) {
                if(productList != null){
                    products.clear();
                    int nbProducts = productList.getProducts().size();
                    ArrayList<SubSimpleAssociation> productsId = productList.getProducts();
                    for(SubSimpleAssociation s : productsId){
                       int finalI = Integer.valueOf(s.getId());
                        viewModel.getProductResponseLiveData(finalI).observe(getViewLifecycleOwner(), new Observer<ProductResponse>() {
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

    @Override
    public void onItemClick(ProductResponse product) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.host_fragment_main_activity, ProductFragment.newInstance(product.getProductInfo().getId()));
        transaction.addToBackStack("ShopFragment");
        transaction.commit();
    }
}