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
import com.cpetsol.cpetsolutions.myaaptha.activity.PrescriptionPage;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Appointmentmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 6/21/2018.
 */

public class DrPastAppointmentsAdapter extends BaseAdapter {
    private final ArrayList<Appointmentmodel> appItmList;
    private Activity activity;
    private LayoutInflater inflater;

    public DrPastAppointmentsAdapter(Activity activity, ArrayList<Appointmentmodel> appItemsList) {
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
        final  int positiontemp=position;
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
   /*     TextView tv4= (TextView) convertView.findViewById(R.id.tv4);*/
        TextView Name= (TextView) convertView.findViewById(R.id.name);
Layout.setVisibility(View.GONE);
        final Appointmentmodel model = appItmList.get(position);
        PatientName.setText(model.getFamilyNames());
        tv1.setText(model.getAppId());
        tv2.setText(model.getHospitalName());
        Log.i("Bhavaniiiiii",model.getAppId());
        tv3.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getBookedDate())+" "+model.getTimeSlot());
      /*  tv4.setText(model.getTimeSlot());*/
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent( activity, PrescriptionPage.class);
                in.putExtra("AppointmentObj",model.getAppId());
                Log.i("Bhavani",model.getAppId());
                in.putExtra("AppointmentObj1",model.getFullName());
                in.putExtra("AppointmentObj2",model.getHospitalName());
                in.putExtra("AppointmentObj3",model.getBookedDate());
                in.putExtra("AppointmentObj4",model.getTimeSlot());
                activity.startActivity(in);
            }
        });

        final JSONObject obj=new JSONObject();
        try {
            obj.accumulate("id",model.getId());
            obj.accumulate("fullName",model.getFullName());
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



        return convertView;
    }


}
