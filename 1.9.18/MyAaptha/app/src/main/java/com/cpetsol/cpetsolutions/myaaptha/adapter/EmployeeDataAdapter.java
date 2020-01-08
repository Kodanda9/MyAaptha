package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.EmployeeModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/12/2018.
 */

public class EmployeeDataAdapter extends BaseAdapter {
    private  Activity activity;
    private ArrayList<EmployeeModel>data;
    private LayoutInflater inflater;
    private Typeface fontAwesomeFont;

    public EmployeeDataAdapter(Activity activity, ArrayList<EmployeeModel> emplist) {
        this.activity=activity;
        this.data=emplist;
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
            view=inflater.inflate(R.layout.employee_lv_row,null);
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        TextView name=(TextView)view.findViewById(R.id.employname);
        TextView hspname=(TextView)view.findViewById(R.id.hospname);
        TextView emp=(TextView)view.findViewById(R.id.emptype);
        TextView remarks=(TextView)view.findViewById(R.id.remarks);

        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);

        final EmployeeModel  model=data.get(i);
        Log.i("sssss",model.toString());
        name.setText(model.getName());
        hspname.setText(model.getHospitalName());
        emp.setText(model.getEmpType());
        remarks.setText(model.getRemarks());

        return view;
    }
}
