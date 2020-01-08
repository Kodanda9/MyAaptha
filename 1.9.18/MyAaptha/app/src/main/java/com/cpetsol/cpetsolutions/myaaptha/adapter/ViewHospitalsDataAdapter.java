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
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalsDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/4/2018.
 */

public class ViewHospitalsDataAdapter extends BaseAdapter {
    private  Activity activity;
    private ArrayList<HospitalsDataModel>model;
    private LayoutInflater inflater;
    private TextView hspnme,mon,tue,wed,thu,fri,sat,sun,duration,price;
    private Typeface fontAwesomeFont;


    public ViewHospitalsDataAdapter(Activity activity, ArrayList<HospitalsDataModel> data) {
        this.activity=activity;
        this.model=data;
    }


    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
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
            view = inflater.inflate(R.layout.hospitaldata_lv_row, null);
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");

   //     TextView  faiSymb = (TextView) view.findViewById(R.id.tvfai_symb);                   faiSymb.setTypeface(fontAwesomeFont);
        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);
        hspnme=(TextView)view.findViewById(R.id.hospitalName);
        sun=(TextView)view.findViewById(R.id.sun);
        mon=(TextView)view.findViewById(R.id.mon);
        tue=(TextView)view.findViewById(R.id.tue);
        wed=(TextView)view.findViewById(R.id.wed);
        thu=(TextView)view.findViewById(R.id.thu);
        fri=(TextView)view.findViewById(R.id.fri);
        sat=(TextView)view.findViewById(R.id.sat);
        duration=(TextView)view.findViewById(R.id.duration);
        price=(TextView)view.findViewById(R.id.price);
        final HospitalsDataModel item=model.get(i);
        if(item.getSunday().equalsIgnoreCase("null-null")){
            sun.setText("---------");
        }else{
            sun.setText(item.getSunday());
        }
        if(item.getMonday().equalsIgnoreCase("null-null")){
            mon.setText("---------");
        }else{
            mon.setText(item.getMonday());
        }
        if(item.getTuesday().equalsIgnoreCase("null-null")){
            tue.setText("---------");
        }else{
            tue.setText(item.getTuesday());
        }
        if(item.getWednesday().equalsIgnoreCase("null-null")){
            wed.setText("---------");
        }else{
            wed.setText(item.getWednesday());
        }
        if(item.getThursday().equalsIgnoreCase("null-null")){
            thu.setText("---------");
        }else{
            thu.setText(item.getThursday());
        }
        if(item.getFriday().equalsIgnoreCase("null-null")){
            fri.setText("---------");
        }else{
            fri.setText(item.getFriday());
        }if(item.getSaturday().equalsIgnoreCase("null-null")){
            sat.setText("---------");
        }else{
            sat.setText(item.getSaturday());
        }


        hspnme.setText(item.getHospitalName());
        duration.setText(item.getDuration());
        price.setText(item.getPrice());
        return view;
    }
}
