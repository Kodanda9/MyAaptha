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
import com.cpetsol.cpetsolutions.myaaptha.adapter.InboxDoctorViewAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.InboxModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Belal on 2/3/2016.
 */

public class DoctorReports extends Fragment {
    private ArrayList<InboxModel> doctorreports;
    private View rootView;
    private ListView doctorData;
    private RelativeLayout NoRecords;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        rootView= inflater.inflate(R.layout.tab2, container, false);
        doctorData=(ListView)rootView.findViewById(R.id.doctorview);
        NoRecords=(RelativeLayout)rootView.findViewById(R.id.rlsuggestlist);
        InboxData runner=new InboxData();
        runner.execute();
        return rootView;
    }
    private class InboxData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
        String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.inBox+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
       //    String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/inBox/900000010501", "GET", null);
            Log.i("Content", Content);
            return Content;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            doctorreports = new ArrayList<InboxModel>();
            InboxModel model = new InboxModel();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
              /*  JSONArray array = jsonObject.getJSONArray("Requested Data");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        model.setId(object.getString("id"));
                        model.setFullName(object.getString("fullName"));
                        model.setRelation(object.getString("relation"));
                        model.setStatus(object.getString("status"));
                        model.setMessage(object.getString("message"));
                        model.setSpecialization(object.getString("specialization"));
                        model.setTimeSlot(object.getString("timeSlot"));
                        model.setBookedDate(object.getString("bookedDate"));
                        model.setHospitalName(object.getString("hospitalName"));
                        model.setAppId(object.getString("appId"));
                        model.setInTime(object.getString("inTime"));
                        model.setDoctorId(object.getString("doctorId"));
                        model.setPoints(object.getString("points"));
                        model.setPrice(object.getString("price"));
                        model.setFamilyNames(object.getString("familyNames"));
                        model.setLocalities(object.getString("localities"));
                        model.setQualification(object.getString("qualification"));
                        model.setCompany(object.getString("company"));
                        model.setFileName(object.getString("fileName"));
                        model.setReportId(object.getString("reportId"));
                        model.setDosage(object.getString("dosage"));
                        model.setHospitalId(object.getString("hospitalId"));
                        model.setDetails(object.getString("details"));
                        inbox.add(model);
                        Log.i("===>", model.toString());
                        InboxAdapter adapter = new InboxAdapter(getActivity(), inbox);
                        requestData.setAdapter(adapter);
                    }
                }*/
                JSONArray array1 = jsonObject.getJSONArray("Doctor Reports");
                InboxModel model1 = new InboxModel();
                if (array1.length() > 0) {
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject object = array1.getJSONObject(i);
                        model1.setId(object.getString("id"));
                        model1.setFullName(object.getString("fullName"));
                        model1.setRelation(object.getString("relation"));
                        model1.setStatus(object.getString("status"));
                        model1.setMessage(object.getString("message"));
                        model1.setSpecialization(object.getString("specialization"));
                        model1.setTimeSlot(object.getString("timeSlot"));
                        model1.setBookedDate(object.getString("bookedDate"));
                        model1.setHospitalName(object.getString("hospitalName"));
                        model1.setAppId(object.getString("appId"));
                        model1.setInTime(object.getString("inTime"));
                        model1.setDoctorId(object.getString("doctorId"));
                        model1.setPoints(object.getString("points"));
                        model1.setPrice(object.getString("price"));
                        model1.setFamilyNames(object.getString("familyNames"));
                        model1.setLocalities(object.getString("localities"));
                        model1.setQualification(object.getString("qualification"));
                        model1.setCompany(object.getString("company"));
                        model1.setFileName(object.getString("fileName"));
                        model1.setReportId(object.getString("reportId"));
                        model1.setDosage(object.getString("dosage"));
                        model1.setHospitalId(object.getString("hospitalId"));
                        model1.setDetails(object.getString("details"));

                        doctorreports.add(model1);
                        Log.i("vani===", doctorreports.toString());
                        InboxDoctorViewAdapter view = new InboxDoctorViewAdapter(getActivity(), doctorreports);
                        doctorData.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }
                else {
                    NoRecords.setVisibility(View.VISIBLE);

                }
              /*  JSONArray array2 = jsonObject.getJSONArray("user Reports");
                InboxModel model2 = new InboxModel();
                if (array2.length() > 0) {
                    for (int i = 0; i < array2.length(); i++) {
                        JSONObject object = array2.getJSONObject(i);
                        model2.setId(object.getString("id"));
                        model2.setFullName(object.getString("fullName"));
                        model2.setRelation(object.getString("relation"));
                        model2.setStatus(object.getString("status"));
                        model2.setMessage(object.getString("message"));
                        model2.setSpecialization(object.getString("specialization"));
                        model2.setTimeSlot(object.getString("timeSlot"));
                        model2.setBookedDate(object.getString("bookedDate"));
                        model2.setHospitalName(object.getString("hospitalName"));
                        model2.setAppId(object.getString("appId"));
                        model2.setInTime(object.getString("inTime"));
                        model2.setDoctorId(object.getString("doctorId"));
                        model2.setPoints(object.getString("points"));
                        model2.setPrice(object.getString("price"));
                        model2.setFamilyNames(object.getString("familyNames"));
                        model2.setLocalities(object.getString("localities"));
                        model2.setQualification(object.getString("qualification"));
                        model2.setCompany(object.getString("company"));
                        model2.setFileName(object.getString("fileName"));
                        model2.setReportId(object.getString("reportId"));
                        model2.setDosage(object.getString("dosage"));
                        model2.setHospitalId(object.getString("hospitalId"));
                        model2.setDetails(object.getString("details"));
                        userreports.add(model2);

                        InboxUserViewAdapter view = new InboxUserViewAdapter(getActivity(), userreports);
                        userData.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }*/
              /*  JSONArray array3 = jsonObject.getJSONArray("Doctor Reviewed Info");
                InboxModel model3 = new InboxModel();
                if (array3.length() > 0) {
                    for (int i = 0; i < array3.length(); i++) {
                        JSONObject object = array3.getJSONObject(i);
                        Log.i("===>", object.toString());
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
                        Log.i("Bhav", model3.toString());
                        InboxReportsAdapter view = new InboxReportsAdapter(getActivity(), Reports);
                        reportss.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
