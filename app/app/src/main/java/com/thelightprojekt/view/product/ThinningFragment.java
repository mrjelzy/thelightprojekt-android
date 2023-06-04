package com.thelightprojekt.view.product;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialCardView slimThinningCard = view.findViewById(R.id.slim_thining_card);
        MaterialCardView thinThinningCard = view.findViewById(R.id.thin_thining_card);
        MaterialCardView extraThinThinningCard = view.findViewById(R.id.extra_thin_thining_card);

        slimThinningCard.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                slimThinningCard.toggle();

                slimThinningCard.setChecked(true);
                slimThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                thinThinningCard.setChecked(false);
                thinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));

                extraThinThinningCard.setChecked(false);
                extraThinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
            }
        });

        thinThinningCard.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                thinThinningCard.toggle();

                thinThinningCard.setChecked(true);
                thinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                slimThinningCard.setChecked(false);
                slimThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));

                extraThinThinningCard.setChecked(false);
                extraThinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
            }
        });

        extraThinThinningCard.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                extraThinThinningCard.toggle();

                extraThinThinningCard.setChecked(true);
                extraThinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.blue));

                slimThinningCard.setChecked(false);
                slimThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));

                thinThinningCard.setChecked(false);
                thinThinningCard.setStrokeColor(ContextCompat.getColor(context, R.color.beautyBlack));
            }
        });
    }
}