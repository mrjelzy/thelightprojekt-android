package com.thelightprojekt.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thelightprojekt.R;
import com.thelightprojekt.model.data.prescription.PrescriptionInfo;
import com.thelightprojekt.model.data.prescription.PrescriptionResponse;

import java.util.ArrayList;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.PrescriptionListViewHolder>{
    private ArrayList<PrescriptionResponse> prescriptions;
    Context context;

    public PrescriptionListAdapter(Context context, ArrayList<PrescriptionResponse> prescriptions) {
        this.prescriptions = prescriptions;
        this.context = context;
    }

    @NonNull
    @Override
    public PrescriptionListAdapter.PrescriptionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_adapter, parent, false);
        return new PrescriptionListAdapter.PrescriptionListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionListAdapter.PrescriptionListViewHolder holder, int position) {

        PrescriptionResponse prescription = prescriptions.get(position);

        holder.num.setText(prescription.getPrescription().getIdPrescription());

        holder.odSPH.setText(prescription.getPrescription().getSphereRight());
        holder.odCYL.setText(prescription.getPrescription().getCylinderRight());
        holder.odAXIS.setText(prescription.getPrescription().getAxisRight());
        holder.odPD.setText(prescription.getPrescription().getPdRight());

        holder.ogSPH.setText(prescription.getPrescription().getSphereLeft());
        holder.ogCYL.setText(prescription.getPrescription().getCylinderLeft());
        holder.ogAXIS.setText(prescription.getPrescription().getAxisLeft());
        holder.ogPD.setText(prescription.getPrescription().getPdRight());
    }

    @Override
    public int getItemCount() {
        return this.prescriptions.size();
    }

    public void setItems(ArrayList<PrescriptionResponse> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public class PrescriptionListViewHolder extends RecyclerView.ViewHolder {

        private TextView num;

        private TextView odSPH;
        private TextView odCYL;
        private TextView odAXIS;
        private TextView odPD;

        private TextView ogSPH;
        private TextView ogCYL;
        private TextView ogAXIS;
        private TextView ogPD;



        public PrescriptionListViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.id_number_prescription);

            odSPH = itemView.findViewById(R.id.sph_od);
            odCYL = itemView.findViewById(R.id.cyl_od);
            odAXIS = itemView.findViewById(R.id.axis_od);
            odPD = itemView.findViewById(R.id.pd_od);

            ogSPH = itemView.findViewById(R.id.sph_og);
            ogCYL = itemView.findViewById(R.id.cyl_og);
            ogAXIS = itemView.findViewById(R.id.axis_og);
            ogPD = itemView.findViewById(R.id.pd_og);
        }

    }
}
