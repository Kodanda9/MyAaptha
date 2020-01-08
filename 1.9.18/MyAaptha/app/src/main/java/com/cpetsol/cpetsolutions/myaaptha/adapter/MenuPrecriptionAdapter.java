package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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

public class MenuPrecriptionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<PrescriptionHistoryModel>data;

    public MenuPrecriptionAdapter(FragmentActivity activity, ArrayList<PrescriptionHistoryModel> arraylist1) {
        this.activity=activity;
        this.data=arraylist1;
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
            view=inflater.inflate(R.layout.prescription_lv_row,null);
        TextView AppID=(TextView)view.findViewById(R.id.appid);
        TextView Specialization=(TextView)view.findViewById(R.id.spec);
        TextView Date=(TextView)view.findViewById(R.id.date);
        TextView Prescription=(TextView)view.findViewById(R.id.prescription);
        final PrescriptionHistoryModel model = data.get(i);

        Log.i("+++++++",model.getSpecialization());

        AppID.setText(model.getAppId());
        Specialization.setText(model.getSpecialization());
        Date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getInTime()));
        return view;
    }
}
