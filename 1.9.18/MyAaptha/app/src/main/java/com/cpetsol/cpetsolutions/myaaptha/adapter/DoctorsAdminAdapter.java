package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.DrAdminModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/13/2018.
 */

public class DoctorsAdminAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private  Activity activity;
    private  ArrayList<DrAdminModel>array;
    public DoctorsAdminAdapter(Activity activity, ArrayList<DrAdminModel> arraylist) {
        this.activity=activity;
        this.array=arraylist;

    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return array.get(i);
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
            view = inflater.inflate(R.layout.dr_admin_lv_row, null);
       TextView Drname=(TextView)view.findViewById(R.id.drname);
        TextView Specialization=(TextView)view.findViewById(R.id.specialization);
        TextView Status=(TextView)view.findViewById(R.id.status);
        final DrAdminModel model=array.get(i);
        Drname.setText(model.getName());
        Specialization.setText(model.getSpecilization());
        Status.setText(model.getStatu());
        return view;
    }
}
