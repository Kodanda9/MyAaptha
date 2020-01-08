package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DoctorsAdminAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.DrAdminModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddWebPageToRoleFragm extends Fragment {

    private ArrayList<DrAdminModel> arraylist;
    private View rootView;
    private ListView Dr_Admin_List;



    public AddWebPageToRoleFragm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_doctors_admin, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView, getActivity());
            Dr_Admin_List=(ListView)rootView.findViewById(R.id.dr_admin_listview);

     DrAdminsRunner runner=new DrAdminsRunner();
            runner.execute();


        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

    private class DrAdminsRunner extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.doctors_Admin,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("sssss",s);
            arraylist=new ArrayList<DrAdminModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        DrAdminModel model=new DrAdminModel();
                        model.setDoctorId(object.getString("doctorId"));
                        model.setName(object.getString("name"));
                        model.setQulification(object.getString("qulification"));
                        model.setExperience(object.getString("experience"));
                        model.setLocation(object.getString("location"));
                        model.setLocalities(object.getString("localities"));
                        model.setSpecilization(object.getString("specilization"));
                        model.setMedicalCouncilNo(object.getString("medicalCouncilNo"));
                        model.setStateMedicalCouncil(object.getString("stateMedicalCouncil"));
                        model.setQualificationYear(object.getString("qualificationYear"));
                        model.setUniversityName(object.getString("universityName"));
                        model.setDocRegYear(object.getString("docRegYear"));
                        model.setStatu(object.getString("status"));
                        model.setDescription(object.getString("description"));
                        model.setDateCreated(object.getString("dateCreated"));
                        model.setDocRegYear(object.getString("docRegYear"));
                        model.setViewStatus(object.getString("viewStatus"));
                        arraylist.add(model);
                        Log.i("model",model.toString());
                        DoctorsAdminAdapter adapter=new DoctorsAdminAdapter(getActivity(),arraylist);
                        Dr_Admin_List.setAdapter(adapter);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
