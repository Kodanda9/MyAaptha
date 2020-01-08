package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionHistoryModel;

import java.util.ArrayList;

/**
 * Created by Admin on 8/1/2018.
 */

public class EducationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<PrescriptionHistoryModel>data;

    public EducationAdapter(FragmentActivity activity, ArrayList<PrescriptionHistoryModel> arraylist2) {
        this.activity=activity;
        this.data=arraylist2;
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
            view=inflater.inflate(R.layout.education_lv_row,null);

        TextView Date=(TextView)view.findViewById(R.id.date);
        TextView Details=(TextView)view.findViewById(R.id.details);
        TextView Education=(TextView)view.findViewById(R.id.qualification);
        final PrescriptionHistoryModel model = data.get(i);

         Education.setText(model.getQualification());
        Details.setText(model.getDetails());
        Date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getInTime()));
        return view;
    }
}
