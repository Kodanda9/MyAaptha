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
import com.cpetsol.cpetsolutions.myaaptha.adapter.UserPastAppointmentsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserApptModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 8/2/2018.
 */
public class UserPastAppointments extends Fragment {

    private View rootView;
    private ListView LVPA;
    private RelativeLayout Norecords;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView= inflater.inflate(R.layout.user_past_appnt, container, false);
        LVPA = (ListView)rootView.findViewById(R.id.lvPA);
        Norecords = (RelativeLayout) rootView.findViewById(R.id.norecords);
        UserPastAppointmetnAsynctask runner3=new UserPastAppointmetnAsynctask();   runner3.execute();

        return rootView;
    }

    private class UserPastAppointmetnAsynctask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private ArrayList<UserApptModel> AppItemsList;
        private  String date;
        private UserPastAppointmentsAdapter adapter;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.pastAppointments+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            //   String content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/pastAppointments/"+SessinSave.getsessin("profile_id",MenuAppointmentActivity.this),"GET",null);
            //  String content= AsyncTaskHelper.makeServiceCall(ApisHelper.userPastAppointment+"?profileId="+ SessinSave.getsessin("profile_id",MenuAppointmentActivity.this),"GET",null);
            //    Log.i("PastAppointments-->",ApisHelper.userPastAppointment+"?profileId="+ SessinSave.getsessin("profile_id",MenuAppointmentActivity.this));
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            AppItemsList = new ArrayList<UserApptModel>();
            try {

                JSONArray array=new JSONArray(result);
                Log.i("array:=>",array.toString());
                if(array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        UserApptModel model = new UserApptModel();
                        model.setId(obj.getString("id"));
                        model.setFullName(obj.getString("fullName"));
                        model.setRelation(obj.getString("relation"));
                        model.setStatus(obj.getString("status"));
                        model.setMessage(obj.getString("message"));
                        model.setSpecialization(obj.getString("specialization"));
                        model.setTimeSlot(obj.getString("timeSlot"));
                        model.setBookedDate(obj.getString("bookedDate"));
                        model.setHospitalName(obj.getString("hospitalName"));
                        model.setAppId(obj.getString("appId"));
                        model.setInTime(obj.getString("inTime"));
                        model.setDoctorId(obj.getString("doctorId"));
                        model.setPoints(obj.getString("points"));
                        model.setPrice(obj.getString("price"));
                        model.setFamilyNames(obj.getString("familyNames"));
                        model.setLocalities(obj.getString("localities"));
                        model.setQualification(obj.getString("qualification"));
                        model.setCompany(obj.getString("company"));
                        model.setFileName(obj.getString("fileName"));
                        model.setPreId(obj.getString("preId"));
                        model.setReportId(obj.getString("reportId"));
                        AppItemsList.add(model);
                    }
                }else {
                    Norecords.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            adapter=new UserPastAppointmentsAdapter(getActivity(),AppItemsList,"userPastAppt");
            LVPA.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }//onPostExecute


    }//AsuncTask class
}