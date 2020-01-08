package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserApptModel;

import java.util.ArrayList;

/**
 * Created by Admin on 5/26/2018.
 */

public class UserAppointmentsAdapter   extends BaseAdapter {
    private final String category;
    private ArrayList<UserApptModel> appItmList;
    private  Activity activity;
    private LayoutInflater inflater;

    public UserAppointmentsAdapter(Activity activity, ArrayList<UserApptModel> appItemsList,String cat) {
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
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        }


        TextView tv1= (TextView) convertView.findViewById(R.id.tv1);
        TextView tv2= (TextView) convertView.findViewById(R.id.tv2);
        TextView tv3= (TextView) convertView.findViewById(R.id.tv3);
        TextView cancel = (TextView) convertView.findViewById(R.id.cancel);
        TextView PatientName= (TextView) convertView.findViewById(R.id.patientname);
      /*  TextView tv4= (TextView) convertView.findViewById(R.id.tv4);*/
        TextView TvDate= (TextView) convertView.findViewById(R.id.tvDate);
        TextView Name= (TextView) convertView.findViewById(R.id.name);
       /* TextView TvTimeSlots= (TextView) convertView.findViewById(R.id.tvTimeSlots);*/

cancel.setVisibility(View.VISIBLE);

     final UserApptModel model = appItmList.get(position);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelTask task=new CancelTask(model.getAppId());
                task.execute();
            }
        });

        if(category.equalsIgnoreCase("userAppt")){
            TvDate.setText("Date");
          /*  TvTimeSlots.setText("Time");*/
            tv1.setText(model.getAppId());
            PatientName.setText(model.getFamilyNames());
            tv2.setText(model.getHospitalName());
            Log.i("====>",model.getFullName().toString());
            tv3.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getBookedDate())+" "+model.getTimeSlot());
          /*  tv4.setText(model.getTimeSlot());*/
            Name.setText(model.getFullName());
        }else{
            TvDate.setText("Date & Time");
         /*   TvTimeSlots.setText("Specialization");*/
            tv1.setText(model.getAppId());
            tv2.setText(model.getHospitalName());
            tv3.setText(AppHelper.ConvertJsonDateWithSec(model.getInTime()) );
            Name.setText(model.getFullName());
        }

      /*  tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent( activity, PrescriptionPage.class);
                in.putExtra("AppointmentObj",model.getAppId());
                Log.i("Bhavani",model.getAppId());
                in.putExtra("AppointmentObj1",model.getFullName());
                in.putExtra("AppointmentObj2",model.getHospitalName());
                in.putExtra("AppointmentObj3",model.getBookedDate());
                activity.startActivity(in);
            }
        });*/


        return convertView;
    }


    private class CancelTask extends AsyncTask<String,String,String>{
        String opid;
        public CancelTask(String appId) {
            this.opid=appId;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.cancelAppointment+"opid","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
