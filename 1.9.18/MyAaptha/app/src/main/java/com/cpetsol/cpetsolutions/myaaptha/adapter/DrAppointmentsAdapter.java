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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Appointmentmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 5/18/2018.
 */

public class DrAppointmentsAdapter  extends BaseAdapter {
    private ArrayList<Appointmentmodel> appItmList;
    private  Activity activity;
    private LayoutInflater inflater;
    private Appointmentmodel model;

    public DrAppointmentsAdapter(Activity activity, ArrayList<Appointmentmodel> appItemsList) {
        this.activity=activity;
        this.appItmList=appItemsList;
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

        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=inflater.inflate(R.layout.appointment_lv_row,null);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        }


        TextView tv1= (TextView) convertView.findViewById(R.id.tv1);
        LinearLayout Layout= (LinearLayout) convertView.findViewById(R.id.drly);
        TextView tv2= (TextView) convertView.findViewById(R.id.tv2);
        TextView tv3= (TextView) convertView.findViewById(R.id.tv3);
        TextView PatientName= (TextView) convertView.findViewById(R.id.patientname);
     /*   TextView tv4= (TextView) convertView.findViewById(R.id.tv4);*/
        TextView Name= (TextView) convertView.findViewById(R.id.name);
        LinearLayout LL= (LinearLayout) convertView.findViewById(R.id.prescll);

        final Appointmentmodel model=appItmList.get(position);
PatientName.setText(model.getFamilyNames());
        Layout.setVisibility(View.GONE);
        tv1.setText(model.getAppId());

        tv2.setText(model.getHospitalName());
//        tv3.setText(model.getBookedDate()+"      "+model.getTimeSlot());
      /*  tv4.setText(model.getTimeSlot());*/
          tv3.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getBookedDate())+" "+model.getTimeSlot());
        final JSONObject obj=new JSONObject();
        try {
            obj.accumulate("id",model.getId());
            obj.accumulate("fullName",model.getFamilyNames());
            obj.accumulate("relation",model.getRelation());
            obj.accumulate("status",model.getStatus());
            obj.accumulate("message",model.getMessage());
            obj.accumulate("specialization",model.getSpecialization());
            obj.accumulate("timeSlot",model.getTimeSlot());
            obj.accumulate("bookedDate",model.getBookedDate());
            obj.accumulate("hospitalName",model.getHospitalName());
            obj.accumulate("appId",model.getAppId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( activity, MenuPrescriptActivity.class);
                in.putExtra("data",model.getId());
                Log.i("ID",model.getId());
                in.putExtra("AppointId",model.getAppId());
                in.putExtra("AppointmentObj",obj.toString());
                activity.startActivity(in);
            }
        });


        return convertView;
    }


}
