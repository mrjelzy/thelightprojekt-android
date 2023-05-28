package com.thelightprojekt.view.product;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.thelightprojekt.R;
import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.viewmodel.ProductViewModel;

public class ThinningFragment extends Fragment {

    private Picasso picasso;
    private Context context;
    private Integer product_id;
    private Integer lens_id;
    private Integer coating_id;
    ProductViewModel viewModel;

    public static ThinningFragment newInstance(int id_product, int id_lens, int id_coating) {
        ThinningFragment fragment = new ThinningFragment();
        Bundle args = new Bundle();
        args.putString("product", String.valueOf(id_product));
        args.putString("lens", String.valueOf(id_lens));
        args.putString("coating", String.valueOf(id_coating));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.picasso = HttpClientInstance.getPicasso(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thinning, container, false);
    }
}