package com.thelightprojekt.view.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thelightprojekt.R;
import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.product.ProductResponse;
import com.thelightprojekt.model.data.SubSimpleAssociation;
import com.thelightprojekt.viewmodel.ProductViewModel;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductFragment extends Fragment {

    Integer product_id;
    Context context;
    ArrayList<SubSimpleAssociation> images;
    Picasso picasso;
    ProductViewModel viewModel;
    TextView title;
    TextView price;
    TextView description;
    ImageCarousel carousel;


    public static ProductFragment newInstance(String id) {
        ProductFragment fragment = new ProductFragment();
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
        context = requireContext();
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.product_title_detail);
        price = view.findViewById(R.id.product_price);
        description = view.findViewById(R.id.product_description);
        carousel = view.findViewById(R.id.carousel);
        List<CarouselItem> list = new ArrayList<>();
        carousel.registerLifecycle(getViewLifecycleOwner());
        viewModel.getProductResponseLiveData(product_id).observe(getViewLifecycleOwner(), new Observer<ProductResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(ProductResponse productResponse) {
                if (productResponse != null) {
                    if(productResponse.getProductInfo().getName() != null)
                        title.setText(productResponse.getProductInfo().getName());

                    if(productResponse.getProductInfo().getPrice() != null){
                        String prix = productResponse.getProductInfo().getPrice().replaceAll("\\.?0*$", "");
                        price.setText(prix + "€");
                    }

                    if(productResponse.getProductInfo().getDescription() != null){
                        String descriptionHtml = productResponse.getProductInfo().getDescription().replaceAll("<.*?>", "");
                        description.setText(descriptionHtml);
                    }


                    images = productResponse.getProductInfo().getAssociations().getImages();
                    Log.d("ProductFragment", "Product images size: " + images.size());
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + HttpClientInstance.getEncryptedKey());
                    for(SubSimpleAssociation i : images){
                        if(!i.getId().equals(productResponse.getProductInfo().getDefaultImage())){
                            String urlImg = "https://www.thelightprojekt.com/api/images/products/"+product_id+"/"+i.getId()+"/";
                            list.add(new CarouselItem(urlImg, headers));
                        }
                    }
                    carousel.setData(list);
                }
            }
        });
    }
}