package com.thelightprojekt.view.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.thelightprojekt.R;
import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.product.ProductInfo;
import com.thelightprojekt.model.data.product.ProductResponse;
import com.thelightprojekt.viewmodel.ProductViewModel;


public class LensFragment extends Fragment {

    private Picasso picasso;
    private Context context;
    private Integer product_id;
    private Integer lens_id = null;
    private ProductViewModel viewModel;

    public static LensFragment newInstance(String id) {
        LensFragment fragment = new LensFragment();
        Bundle args = new Bundle();
        args.putString("product", id);
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
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.init();
        if (getArguments() != null)
            product_id = Integer.valueOf((String) getArguments().get("product"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lens, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView productTitle= view.findViewById(R.id.product_title_lens);
        ImageView productImage = view.findViewById(R.id.product_image_lens);
        MaterialCardView withPrescription = view.findViewById(R.id.with_prescription_lens);
        MaterialCardView withoutPrescription = view.findViewById(R.id.without_prescription_lens);
        MaterialButton nextButton = view.findViewById(R.id.button_next_lens);

        withPrescription.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                withPrescription.toggle();

                withPrescription.setChecked(true);
                withPrescription.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                withoutPrescription.setChecked(false);
                withoutPrescription.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
                lens_id = 26 ;
            }
            });

        withoutPrescription.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                withoutPrescription.toggle();

                withoutPrescription.setChecked(true);
                withoutPrescription.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                withPrescription.setChecked(false);
                withPrescription.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
                lens_id =  27 ;
            }
        });


        viewModel.getProductResponseLiveData(product_id).observe(getViewLifecycleOwner(), new Observer<ProductResponse>() {
            @Override
            public void onChanged(ProductResponse productResponse) {
                ProductInfo product = productResponse.getProductInfo();
                if(product.getName() != null)
                    productTitle.setText(product.getName());

                String id_img = product.getDefaultImage();

                String urlImg = "https://www.thelightprojekt.com/api/images/products/"+product_id+"/"+id_img+"/";

                Picasso picasso = HttpClientInstance.getPicasso(context);

                picasso.load(urlImg)
                        .fit()
                        .centerCrop()
                        .into(productImage);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (withoutPrescription.isChecked() || withPrescription.isChecked()) {
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.host_fragment_main_activity, CoatingFragment.newInstance(product_id, lens_id))
                                .setReorderingAllowed(true)
                                .addToBackStack("CoatingChoosingFragment")
                                .commit();
                    }
                }
            });
    }
}