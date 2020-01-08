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
import com.cpetsol.cpetsolutions.myaaptha.adapter.MenuPrecriptionAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity.Listview;

/**
 * Created by Admin on 8/1/2018.
 */

public class PrescriptionsHistory extends Fragment {


    private View rootView;
    private ImageView Norecords;
    private String s,sa;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }

      s= getArguments().getString("APPID");
      sa = getArguments().getString("PFID");


        Toast.makeText(getActivity(), ""+s+sa, Toast.LENGTH_SHORT).show();

        rootView = inflater.inflate(R.layout.prescription_history_frag, container, false);
        Listview = (ListView) rootView.findViewById(R.id.listview);
        Norecords = (ImageView) rootView.findViewById(R.id.norecords);
      PrescriptionHistoryRunner runner = new PrescriptionHistoryRunner();
        runner.execute();
        return rootView;
    }


    private class PrescriptionHistoryRunner extends AsyncTask<String, String, String> {
        private ArrayList<PrescriptionHistoryModel> arraylist1;
        private ArrayList<PrescriptionHistoryModel> arraylist2;
        private ArrayList<PrescriptionHistoryModel> arraylist3;


        @Override
        protected String doInBackground(String... strings) {
                String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allProfileDetails+sa+"&appId="+s,"GET",null);
        //    String Content = AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/allProfileDetails?pid=900000030501&appId=78", "GET", null);
            Log.i("=====>", Content);


            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arraylist1 = new ArrayList<PrescriptionHistoryModel>();
            arraylist2 = new ArrayList<PrescriptionHistoryModel>();
            arraylist3 = new ArrayList<PrescriptionHistoryModel>();
            PrescriptionHistoryModel model = new PrescriptionHistoryModel();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("datesList");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        model.setId(object1.getString("id"));
                        model.setFullName(object1.getString("fullName"));
                        model.setStatus(object1.getString("status"));
                        model.setRelation(object1.getString("relation"));
                        model.setMessage(object1.getString("message"));
                        model.setSpecialization(object1.getString("specialization"));
                        model.setTimeSlot(object1.getString("timeSlot"));
                        model.setBookedDate(object1.getString("bookedDate"));
                        model.setHospitalName(object1.getString("hospitalName"));
                        model.setAppId(object1.getString("appId"));
                        model.setInTime(object1.getString("inTime"));
                        model.setEndTime(object1.getString("endTime"));
                        model.setDoctorId(object1.getString("doctorId"));
                        model.setPoints(object1.getString("points"));
                        model.setPrice(object1.getString("price"));
                        model.setFamilyNames(object1.getString("familyNames"));
                        model.setLocalities(object1.getString("localities"));
                        model.setQualification(object1.getString("qualification"));
                        model.setCompany(object1.getString("company"));
                        model.setFileName(object1.getString("fileName"));
                        model.setPreId(object1.getString("preId"));
                        model.setReportId(object1.getString("reportId"));
                        model.setDosage(object1.getString("dosage"));
                        model.setHospitalId(object1.getString("hospitalId"));
                        model.setDetails(object1.getString("details"));
                        model.setStartTime(object1.getString("startTime"));
                        model.setOutTime(object1.getString("outTime"));
                        model.setDoctorName(object1.getString("doctorName"));
                        model.setMobileNo(object1.getString("mobileNo"));
                        arraylist1.add(model);
                    }

                    MenuPrecriptionAdapter adapter = new MenuPrecriptionAdapter(getActivity(), arraylist1);
                    Listview.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(),"No Records Found",Toast.LENGTH_LONG).show();
           //         Norecords.setVisibility(View.VISIBLE);
                }


           /* JSONArray array2=object.getJSONArray("employment");
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
