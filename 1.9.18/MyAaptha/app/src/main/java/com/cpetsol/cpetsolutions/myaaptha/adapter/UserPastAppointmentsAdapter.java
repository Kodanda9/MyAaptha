package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.PrescriptionPage;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserApptModel;

import java.util.ArrayList;

/**
 * Created by Admin on 6/23/2018.
 */

public class UserPastAppointmentsAdapter extends BaseAdapter {
    private final String category;
    private ArrayList<UserApptModel> appItmList;
    private Activity activity;
    private LayoutInflater inflater;

    public UserPastAppointmentsAdapter(Activity activity, ArrayList<UserApptModel> appItemsList,String cat) {
        this.activity=activity;
        this.appItmList=appItemsList;
        this.category =cat;
    }



    @Override
    public int getCount() {
        return appItmList.size();
    }
    @Override
    public Object getItem(int i) {
        return appItmList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  int positiontemp=position;
        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=inflater.inflate(R.layout.appointment_lv_row,null);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        }

        TextView tv1= (TextView) convertView.findViewById(R.id.tv1);
        TextView tv2= (TextView) convertView.findViewById(R.id.tv2);
        final TextView tv3= (TextView) convertView.findViewById(R.id.tv3);
        TextView PatientName= (TextView) convertView.findViewById(R.id.patientname);
      /*  TextView tv4= (TextView) convertView.findViewById(R.id.tv4);*/
        TextView Name= (TextView) convertView.findViewById(R.id.name);
        TextView TvDate= (TextView) convertView.findViewById(R.id.tvDate);/*
    /*  *//*  TextView TvTimeSlots= (TextView) convertView.findViewById(R.id.tvTimeSlots);*/




        final UserApptModel model = appItmList.get(position);
        PatientName.setText(model.getFamilyNames());

        if(category.equalsIgnoreCase("userAppt")){
            TvDate.setText("Date");
        /*    TvTimeSlots.setText("Time");*/
            tv1.setText(model.getAppId());
            tv2.setText(model.getFullName());
            Log.i("Bhavani",model.getFullName().toString());
            tv3.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getBookedDate())+" "+model.getTimeSlot());
         /*   tv4.setText(model.getTimeSlot());*/
            Name.setText(model.getFullName());
        }else{
            TvDate.setText("Date & Time");
      /*      TvTimeSlots.setText("Specialization");*/
            tv1.setText(model.getAppId());
            tv2.setText(model.getHospitalName());
            tv3.setText(AppHelper.ConvertJsonDateWithSec(model.getInTime()) );
        /*    tv4.setText(model.getSpecialization());*/
            Name.setText(model.getFamilyNames());
        }

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent( activity, PrescriptionPage.class);
                in.putExtra("AppointmentObj",model.getAppId());
                Log.i("Bhavani",model.getAppId());
                in.putExtra("AppointmentObj1",model.getFamilyNames());
                in.putExtra("AppointmentObj2",model.getHospitalName());
                in.putExtra("AppointmentObj3",tv3.getText().toString());
                activity.startActivity(in);
            }
        });


        return convertView;
    }


}