package com.thelightprojekt.view.product;

import android.content.Context;
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

public class CoatingFragment extends Fragment {

    private Picasso picasso;
    private Context context;
    private Integer product_id;
    private Integer lens_id;
    private Integer coating_id;
    ProductViewModel viewModel;

    public static CoatingFragment newInstance(int id_product, int id_lens) {
        CoatingFragment fragment = new CoatingFragment();
        Bundle args = new Bundle();
        args.putString("product", String.valueOf(id_product));
        args.putString("lens", String.valueOf(id_lens));
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
        if (getArguments() != null){
            product_id = Integer.valueOf((String) getArguments().get("product"));
            lens_id = Integer.valueOf((String) getArguments().get("lens"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView productTitle= view.findViewById(R.id.product_title_coating);
        ImageView productImage = view.findViewById(R.id.product_image_coating);

        MaterialCardView blueLightFilterCoating = view.findViewById(R.id.blue_light_filter_coating);
        MaterialCardView clearCoating = view.findViewById(R.id.clear_coating);
        MaterialButton nextButton = view.findViewById(R.id.button_next_coating);

        if(lens_id == 27){
            nextButton.setText("ADD TO CART");
        }

        blueLightFilterCoating.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                blueLightFilterCoating.toggle();

                blueLightFilterCoating.setChecked(true);
                blueLightFilterCoating.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                clearCoating.setChecked(false);
                clearCoating.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
                coating_id = 31;
            }
        });

        clearCoating.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                clearCoating.toggle();

                clearCoating.setChecked(true);
                clearCoating.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                blueLightFilterCoating.setChecked(false);
                blueLightFilterCoating.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
                coating_id =  32;
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
                if ((blueLightFilterCoating.isChecked() || clearCoating.isChecked()) && lens_id == 26) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, ThinningFragment.newInstance(product_id, lens_id, coating_id))
                            .setReorderingAllowed(true)
                            .addToBackStack("ThinningChoosingFragment")
                            .commit();
                }else if ((blueLightFilterCoating.isChecked() || clearCoating.isChecked()) && lens_id == 27){
                    System.out.println("Je suis sans correction donc ajout au panier ");
                }else{
                    System.out.println("Je n'ai rien check");
                }
            }
        });
    }
}