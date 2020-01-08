package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.UserAppointmentsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserApptModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 8/2/2018.
 */
public class UserAppointments extends Fragment {

    private View rootView;
    private ListView LVA;
    private RelativeLayout Norecords;


    public UserAppointments() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView = inflater.inflate(R.layout.user_appnt, container, false);
        LVA = (ListView) rootView.findViewById(R.id.lvA);
        Norecords = (RelativeLayout) rootView.findViewById(R.id.norecords);

        UserAppointmetnAsynctask runner = new UserAppointmetnAsynctask();
        runner.execute();

        return rootView;
    }

    private class UserAppointmetnAsynctask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private ArrayList<UserApptModel> AppItemsList;
        private UserAppointmentsAdapter adapter;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.userAppointments + "/" + SessinSave.getsessin("profile_id", getActivity()) + "", "GET", null);

            Log.i("UsetApts-->", ApisHelper.userAppointments + "/" + SessinSave.getsessin("profile_id", getActivity()));
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("Result-->", result);
            AppItemsList = new ArrayList<UserApptModel>();
            try {

                JSONArray mainArray = new JSONArray(result);
                JSONArray array = mainArray.getJSONArray(0);
                if (array.length() > 0) {
                    Norecords.setVisibility(View.GONE);
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
                } else {
                    Norecords.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter = new UserAppointmentsAdapter(getActivity(), AppItemsList, "userAppt");
            LVA.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }//onPostExecute

    }
}