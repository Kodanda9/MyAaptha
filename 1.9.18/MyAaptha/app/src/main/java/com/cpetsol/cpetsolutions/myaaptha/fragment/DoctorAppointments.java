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
import com.cpetsol.cpetsolutions.myaaptha.adapter.DrAppointmentsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Appointmentmodel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 8/2/2018.
 */
public class DoctorAppointments extends Fragment {

    private View rootView;
    private ListView LVADR;
    private RelativeLayout Norecords;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView= inflater.inflate(R.layout.doctor_appnt, container, false);
        LVADR = (ListView)rootView.findViewById(R.id.lvAdr);
        Norecords = (RelativeLayout) rootView.findViewById(R.id.norecords);
        DrAppointmetnAsynctask runner=new DrAppointmetnAsynctask();   runner.execute();
        return rootView;
    }

    private class DrAppointmetnAsynctask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private ArrayList<Appointmentmodel> AppItemsList;
        private DrAppointmentsAdapter adapter;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.docAppointments+"/"+ SessinSave.getsessin("profile_id",getActivity())+"","GET",null);
            Log.i("Appt Api-->",AsyncTaskHelper.makeServiceCall(ApisHelper.docAppointments+"/"+ SessinSave.getsessin("profile_id",getActivity())+"","GET",null));
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            AppItemsList = new ArrayList<Appointmentmodel>();
            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Appointmentmodel model = new Appointmentmodel();
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
                        model.setEndTime(obj.getString("endTime"));
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
                        model.setDosage(obj.getString("dosage"));
                        model.setHospitalId(obj.getString("hospitalId"));
                        model.setDetails(obj.getString("details"));
                        model.setStartTime(obj.getString("startTime"));
                        model.setOutTime(obj.getString("outTime"));
                        model.setDoctorName(obj.getString("doctorName"));
                        model.setMobileNo(obj.getString("mobileNo"));
                        model.setLabTestName(obj.getString("labTestName"));
                        model.setDrugName(obj.getString("drugName"));
                        model.setDrugType(obj.getString("drugType"));
                        model.setTestName(obj.getString("testName"));
                        AppItemsList.add(model);
                    }
                }else {
                    Norecords.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            adapter=new DrAppointmentsAdapter(getActivity(),AppItemsList);
            LVADR.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }//onPostExecute


    }//AsuncTask class
}