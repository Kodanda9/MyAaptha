package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.EducationAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity.EducationListview;
import static com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity.Norecords;

/**
 * Created by Admin on 8/1/2018.
 */

public class EducationDetails extends Fragment {
    private View rootView;
    private String Appid,pfid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        Appid= getArguments().getString("APPID");
        pfid = getArguments().getString("PFID");
        rootView = inflater.inflate(R.layout.education_frag, container, false);

        EducationListview = (ListView) rootView.findViewById(R.id.educationListview);

        Norecords = (ImageView) rootView.findViewById(R.id.norecords);
        PrescriptionHistoryRunner runner=new PrescriptionHistoryRunner();
        runner.execute();

        return rootView;
    }

    private class PrescriptionHistoryRunner extends AsyncTask<String,String,String> {
        private ArrayList<PrescriptionHistoryModel> arraylist1;
        private ArrayList<PrescriptionHistoryModel> arraylist2;
        private ArrayList<PrescriptionHistoryModel> arraylist3;


        @Override
        protected String doInBackground(String... strings) {
               String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allProfileDetails+pfid+"&appId="+Appid,"GET",null);
 //           String Content = AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/allProfileDetails?pid=900000030501&appId=78", "GET", null);
            Log.i("=====>", Content);


            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            arraylist2 = new ArrayList<PrescriptionHistoryModel>();


            try {
                JSONObject object = new JSONObject(s);
                JSONArray array1 = object.getJSONArray("education");
                if(array1.length()>0){
                    for(int i=0;i<array1.length();i++){
                        JSONObject object2=array1.getJSONObject(i);
                        PrescriptionHistoryModel model = new PrescriptionHistoryModel();
                        Log.i("++++",object2.getString("qualification"));
                        model.setQualification(object2.getString("qualification"));
                        Log.i("+++++++++",object2.getString("qualification"));
                        model.setInTime(object2.getString("inTime"));
                        model.setDetails(object2.getString("message"));
                        arraylist2.add(model);

                    }
                    EducationAdapter adapter1 = new EducationAdapter(getActivity(),arraylist2);
                    EducationListview.setAdapter(adapter1);
                } else {
                    Toast.makeText(getActivity(),"No Records Found",Toast.LENGTH_LONG).show();
                    //         Norecords.setVisibility(View.VISIBLE);
                }
               /* if (array1.length() > 0) {
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject object2 = array1.getJSONObject(i);
                        model.setId(object2.getString("id"));
                        model.setInTime(object2.getString("inTime"));
                        model.setQualification(object2.getString("qualification"));
                        model.setDetails(object2.getString("message"));
                        arraylist2.add(model);
                        Log.i("+++++",arraylist2.toString());
                    }
                    EducationAdapter adapter1 = new EducationAdapter(getActivity(),arraylist2);
                    EducationListview.setAdapter(adapter1);
                } else {
                    Norecords.setVisibility(View.VISIBLE);
                }*/


/*

           JSONArray array2=object.getJSONArray("employment");
            if(array2.length()>0){
                for(int i=0;i<array2.length();i++){
                    JSONObject object3=array1.getJSONObject(i);
                    model.setId(object3.getString("id"));
                    model.setFullName(object3.getString("fullName"));
                    model.setStatus(object3.getString("status"));
                    model.setRelation(object3.getString("relation"));
                    model.setMessage(object3.getString("message"));
                    model.setSpecialization(object3.getString("specialization"));
                    model.setTimeSlot(object3.getString("timeSlot"));
                    model.setBookedDate(object3.getString("bookedDate"));
                    model.setHospitalName(object3.getString("hospitalName"));
                    model.setAppId(object3.getString("appId"));
                    model.setInTime(object3.getString("inTime"));
                    model.setEndTime(object3.getString("endTime"));
                    model.setDoctorId(object3.getString("doctorId"));
                    model.setPoints(object3.getString("points"));
                    model.setPrice(object3.getString("price"));
                    model.setFamilyNames(object3.getString("familyNames"));
                    model.setLocalities(object3.getString("localities"));
                    model.setQualification(object3.getString("qualification"));
                    model.setCompany(object3.getString("company"));
                    model.setFileName(object3.getString("fileName"));
                    model.setPreId(object3.getString("preId"));
                    model.setReportId(object3.getString("reportId"));
                    model.setDosage(object3.getString("dosage"));
                    model.setHospitalId(object3.getString("hospitalId"));
                    model.setDetails(object3.getString("details"));
                    model.setStartTime(object3.getString("startTime"));
                    model.setOutTime(object3.getString("outTime"));
                    model.setDoctorName(object3.getString("doctorName"));
                    model.setMobileNo(object3.getString("mobileNo"));
                    arraylist3.add(model);
                }
                EmploymentAdapter adapter2=new EmploymentAdapter(MenuPrescriptActivity.this,arraylist3);
        //        EmployLV.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }
*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
