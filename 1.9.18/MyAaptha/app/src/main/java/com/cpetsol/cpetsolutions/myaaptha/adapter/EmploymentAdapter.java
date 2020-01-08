package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionHistoryModel;

import java.util.ArrayList;

/**
 * Created by Admin on 8/1/2018.
 */
public class EmploymentAdapter  extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<PrescriptionHistoryModel> data;
    public EmploymentAdapter(MenuPrescriptActivity menuPrescriptActivity, ArrayList<PrescriptionHistoryModel> arraylist) {
        this.activity=menuPrescriptActivity;
        this.data=arraylist;
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
        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            view=inflater.inflate(R.layout.employ_lv_row,null);
       /* TextView Education=(TextView)view.findViewById(R.id.education);
        TextView Date=(TextView)view.findViewById(R.id.date);
        TextView Details=(TextView)view.findViewById(R.id.details);
        final PrescriptionHistoryModel model = data.get(i);

        Education.setText(model.getQualification());
        Details.setText(model.getDetails());
        Date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getInTime()));*/
        return view;
    }
}
