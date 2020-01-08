package com.cpetsol.cpetsolutions.myaaptha.fragment;

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
import com.cpetsol.cpetsolutions.myaaptha.adapter.InboxReportsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.InboxModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DoctorReviewInf extends Fragment {

    private RelativeLayout NoRecords;


    public DoctorReviewInf() {
        // Required empty public constructor
    }


    private ArrayList<InboxModel> Reports;
    private View rootView;
    private ListView reportss;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView = inflater.inflate(R.layout.tab4, container, false);
        reportss = (ListView) rootView.findViewById(R.id.reportsinformation);
        NoRecords = (RelativeLayout) rootView.findViewById(R.id.rlsuggestlist);
        InboxData runner = new InboxData();
        runner.execute();
        return rootView;
    }
    private class InboxData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.inBox+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            //      String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/inBox/900000010501","GET",null);
            Log.i("Content",Content);
            return Content;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Reports= new ArrayList<InboxModel>();


            try {
                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                JSONArray array3=jsonObject.getJSONArray("Doctor Reviewed Info");
                InboxModel model3=new InboxModel();
                if(array3.length()>0){
                    for(int i=0;i<array3.length();i++){
                        JSONObject object=array3.getJSONObject(i);
                        Log.i("$$$$",object.toString());
                        model3.setId(object.getString("id"));
                        model3.setFullName(object.getString("fullName"));
                        model3.setRelation(object.getString("relation"));
                        model3.setStatus(object.getString("status"));
                        model3.setMessage(object.getString("message"));
                        model3.setSpecialization(object.getString("specialization"));
                        model3.setTimeSlot(object.getString("timeSlot"));
                        model3.setBookedDate(object.getString("bookedDate"));
                        model3.setHospitalName(object.getString("hospitalName"));
                        model3.setAppId(object.getString("appId"));
                        model3.setInTime(object.getString("inTime"));
                        model3.setDoctorId(object.getString("doctorId"));
                        model3.setPoints(object.getString("points"));
                        model3.setPrice(object.getString("price"));
                        model3.setFamilyNames(object.getString("familyNames"));
                        model3.setLocalities(object.getString("localities"));
                        model3.setQualification(object.getString("qualification"));
                        model3.setCompany(object.getString("company"));
                        model3.setFileName(object.getString("fileName"));
                        model3.setReportId(object.getString("reportId"));
                        model3.setDosage(object.getString("dosage"));
                        model3.setHospitalId(object.getString("hospitalId"));
                        model3.setDetails(object.getString("details"));
                        Reports.add(model3);
                        Log.i("$$$$$$$$$$$$$",model3.toString());
                        InboxReportsAdapter view=new InboxReportsAdapter(getActivity(),Reports);
                        reportss.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }
                else {
                    NoRecords.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
