package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DrPastAppointmentsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Appointmentmodel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 8/2/2018.
 */
public class DoctorPastAppointments extends Fragment {

    private View rootView;
    private ListView LVPADR;
    private RelativeLayout Norecords;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView= inflater.inflate(R.layout.doctor_past_appnt, container, false);
        LVPADR = (ListView)rootView.findViewById(R.id.lvPAdr);
        Norecords = (RelativeLayout) rootView.findViewById(R.id.norecords);

        DrPastAppointmetnAsynctask runner1=new DrPastAppointmetnAsynctask();   runner1.execute();
        return rootView;
    }

    private class DrPastAppointmetnAsynctask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private ArrayList<Appointmentmodel> AppItemsList;
        private DrPastAppointmentsAdapter adapter;
        private  String date;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        }
        @Override
        protected String doInBackground(String... params) {
            //http://192.168.1.15:8080/pp/pastAppointment?profileId=900000041502&past=2018-02-24
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.pastAppointment+"?profileId="+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            Log.i("Past Appt url-->",ApisHelper.pastAppointment+"?profileId="+ SessinSave.getsessin("profile_id",getActivity()));
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            AppItemsList = new ArrayList<Appointmentmodel>();
            try {
                JSONArray array=new JSONArray(result);
                Log.i("array:=>",array.toString());
                if(array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Appointmentmodel model = new Appointmentmodel();
                        model.setId(obj.getString("id"));
                        model.setFullName(obj.getString("familyNames"));
                        model.setRelation(obj.getString("relation"));
                        model.setStatus(obj.getString("status"));
                        model.setFamilyNames(obj.getString("familyNames"));
                        model.setMessage(obj.getString("message"));
                        model.setSpecialization(obj.getString("specialization"));
                        model.setTimeSlot(obj.getString("timeSlot"));
                        model.setBookedDate(obj.getString("bookedDate"));
                        model.setHospitalName(obj.getString("hospitalName"));
                        model.setAppId(obj.getString("appId"));
                        AppItemsList.add(model);
                    }
                }else {
                    Norecords.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            adapter=new DrPastAppointmentsAdapter(getActivity(),AppItemsList);
            LVPADR.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }//onPostExecute


    }//AsuncTask class
}