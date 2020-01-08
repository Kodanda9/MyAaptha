package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.Search_Medicime_Model;

import java.util.ArrayList;

/**
 * Created by Admin on 6/4/2018.
 */

public class Search_Medicine_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Search_Medicime_Model>data;
    private ArrayList<String> selectedStrings;

    public Search_Medicine_Adapter(Activity activity, ArrayList<Search_Medicime_Model> rowItems) {
        this.activity=activity;
        this.data=rowItems;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
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
            view = inflater.inflate(R.layout.search_lv_row, null);
        TextView drugname=(TextView)view.findViewById(R.id.drugname);
        TextView drugtype=(TextView)view.findViewById(R.id.drugtype);
        TextView Dosage=(TextView)view.findViewById(R.id.dosage);
        TextView qty=(TextView)view.findViewById(R.id.quantity);
        final CheckBox check=(CheckBox)view.findViewById(R.id.checkbox);

        final Search_Medicime_Model medicine=data.get(i);
        drugname.setText(medicine.getDrugName());
        drugtype.setText(medicine.getDrugType());
        Dosage.setText(medicine.getDosage());
        qty.setText(medicine.getQty());
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    ArrayList<String> scripts = new ArrayList<String>();
                    scripts.add(medicine.getDrugName()+"-"+medicine.getDosage()+"-"+medicine.getDrugType()+"-"+medicine.getQty());

              //      String s=medicine.getDrugName()+"-"+medicine.getDosage()+"-"+medicine.getDrugType()+"-"+medicine.getQty();
                    Toast.makeText(activity,""+scripts,Toast.LENGTH_LONG).show();
                }else{

                }

            }
        });

        return view;
    }
}
