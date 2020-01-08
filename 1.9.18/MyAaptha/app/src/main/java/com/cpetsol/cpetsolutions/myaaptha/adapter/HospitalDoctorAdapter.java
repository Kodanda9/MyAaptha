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
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalDoctorsModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/13/2018.
 */

public class HospitalDoctorAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HospitalDoctorsModel>array;
    private LayoutInflater inflater;
    private Typeface fontAwesomeFont;


    public HospitalDoctorAdapter(Activity activity, ArrayList<HospitalDoctorsModel> hosplist) {
        this.activity=activity;
        this.array=hosplist;
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
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        if (view == null)
            view = inflater.inflate(R.layout.hospital_dr_lv_row, null);
        TextView Drname=(TextView)view.findViewById(R.id.docname);
        TextView Sunday=(TextView)view.findViewById(R.id.sun);
        TextView Monday=(TextView)view.findViewById(R.id.mon);
        TextView Tuesday=(TextView)view.findViewById(R.id.tue);
        TextView Wednesday=(TextView)view.findViewById(R.id.wed);
        TextView Thursday=(TextView)view.findViewById(R.id.thu);
        TextView Friday=(TextView)view.findViewById(R.id.fri);
        TextView Saturday=(TextView)view.findViewById(R.id.sat);
        TextView Duration=(TextView)view.findViewById(R.id.dur);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);

        final HospitalDoctorsModel model=array.get(i);
        Drname.setText(model.getDoctorName());
        Sunday.setText(model.getSunday());
        Monday.setText(model.getMonday());
        Tuesday.setText(model.getTuesday());
        Wednesday.setText(model.getWednesday());
        Thursday.setText(model.getThursday());
        Friday.setText(model.getFriday());
        Saturday.setText(model.getSaturday());
        Duration.setText(model.getDuration());
        return view;
    }
}
