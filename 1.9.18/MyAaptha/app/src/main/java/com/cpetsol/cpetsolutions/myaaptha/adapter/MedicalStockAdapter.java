package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.MedicineStockModel;

import java.util.ArrayList;

/**
 * Created by Admin on 5/30/2018.
 */

public class MedicalStockAdapter extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<MedicineStockModel> userItems;
    private LayoutInflater inflater;
    private TextView Drugname,DrugType,Threshold,Quantity,Remarks,Dosage,AgencyName;

    public MedicalStockAdapter(Activity activity, ArrayList<MedicineStockModel> rowItems) {
        this.activity=activity;
        this.userItems=rowItems;
    }


    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int i) {
        return userItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.stock_details_lvrow, null);
        Drugname=(TextView)view.findViewById(R.id.drugname);
        DrugType=(TextView)view.findViewById(R.id.drugtype);
        Threshold=(TextView)view.findViewById(R.id.threshold);
        Dosage=(TextView)view.findViewById(R.id.dosage);
        Quantity=(TextView)view.findViewById(R.id.quantity);
        Remarks=(TextView)view.findViewById(R.id.remarks);
        AgencyName=(TextView)view.findViewById(R.id.agencyname);

        final MedicineStockModel model=userItems.get(i);
        Drugname.setText(model.getDrugName());
        DrugType.setText(model.getDrugType());
        Threshold.setText(model.getThreshold());
        Dosage.setText(model.getDosage());
        Quantity.setText(model.getQuantity());
        Remarks.setText(model.getRemarks());
        AgencyName.setText(model.getAgencyName());
        return view;
    }
}
