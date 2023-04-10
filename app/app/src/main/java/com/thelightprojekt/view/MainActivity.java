package com.thelightprojekt.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.thelightprojekt.R;
import com.thelightprojekt.model.data.ProductInfo;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {

    ProductViewModel productViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView productText = findViewById(R.id.product_title);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        final Observer<ProductResponse> nameObserver = new Observer<ProductResponse>() {
            @Override
            public void onChanged(@Nullable final ProductResponse product) {
                if(product != null)
                    productText.setText(product.getProductInfo().getName());
            }
        };

        productViewModel.getProductResponseLiveData(1).observe(this, nameObserver);

    }
}