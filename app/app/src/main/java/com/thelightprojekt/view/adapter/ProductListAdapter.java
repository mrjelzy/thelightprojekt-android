package com.thelightprojekt.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.thelightprojekt.R;
import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.ProductResponse;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    // FOR DATA
    private ArrayList<ProductResponse> products;
    Context context;

    // CONSTRUCTOR
    public ProductListAdapter(Context context, ArrayList<ProductResponse> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter, parent, false);
        return new ProductListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductResponse product = products.get(position);

        holder.productTitle.setText(product.getProductInfo().getName());


        OkHttpClient httpClient = HttpClientInstance.getClient();

        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(httpClient))
                .build();

        String id_product = product.getProductInfo().getId();
        String id_img = product.getProductInfo().getAssociations().getImages().get(0).getId();

        String urlImg = "https://www.thelightprojekt.com/api/images/products/"+id_product+"/"+id_img+"/";

        picasso.load(urlImg)
                .fit()
                .centerCrop()
                .into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public void setItems(ArrayList<ProductResponse> products) {
        this.products = products;
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitle;
        private ImageView productImage;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.product_title);
            productImage = itemView.findViewById(R.id.image_product);
        }

    }
}
