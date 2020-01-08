package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalOwnerModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/13/2018.
 */

public class HospitalOwnerAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<HospitalOwnerModel>array;
    private Typeface fontAwesomeFont;


    public HospitalOwnerAdapter(Activity activity, ArrayList<HospitalOwnerModel> arraylist) {
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
            view = inflater.inflate(R.layout.hospital_owner_lv_row, null);

        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");

        TextView HospitalName=(TextView)view.findViewById(R.id.hospitalname);
        TextView CategoryType=(TextView)view.findViewById(R.id.categorytype);
        TextView EmploymentName=(TextView)view.findViewById(R.id.employmenttype);

        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);

        final HospitalOwnerModel model=array.get(i);
        HospitalName.setText(model.getHospitalName());
        CategoryType.setText(model.getEmploymentType());
        EmploymentName.setText(model.getName());
  return view;
    }
}
