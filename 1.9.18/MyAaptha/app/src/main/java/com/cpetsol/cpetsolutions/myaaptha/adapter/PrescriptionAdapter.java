package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.PrescriptionPage;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionModel;

import java.util.ArrayList;

/**
 * Created by Admin on 6/22/2018.
 */

public class PrescriptionAdapter extends BaseAdapter {
    private final ArrayList<PrescriptionModel> userItems;
    private Activity activity;
    private LayoutInflater inflater;

    public PrescriptionAdapter(PrescriptionPage prescriptionPage, ArrayList<PrescriptionModel> rowItems) {
        activity=prescriptionPage;
        userItems = rowItems;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.prescriptionmedlvrow, null);

        if (position % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightblue));
        }


        final PrescriptionModel model=userItems.get(position);
        TextView drug=(TextView)view.findViewById(R.id.drug);
        TextView dosage=(TextView)view.findViewById(R.id.dosage);
        TextView quantity=(TextView)view.findViewById(R.id.qty);

        TextView instruction=(TextView)view.findViewById(R.id.instruction);


        drug.setText(model.getDrugName());
        dosage.setText(model.getDosage());
        quantity.setText(model.getQty()+model.getDrugType());
      /*  drugtype.setText(model.getDrugType());*/
        instruction.setText(model.getInstruction());
        return view;
    }
}
