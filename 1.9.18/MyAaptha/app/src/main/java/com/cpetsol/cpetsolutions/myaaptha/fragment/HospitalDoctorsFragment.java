package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.HospitalDoctorAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalDoctorsModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HospitalDoctorsFragment extends Fragment {

    private View rootView;
    private ListView hsplist;



    public HospitalDoctorsFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_hospital_doctors, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView, getActivity());
             hsplist=(ListView)rootView.findViewById(R.id.hospitallistview);
            HospitalListRunner runner=new HospitalListRunner();
            runner.execute();


        } catch (InflateException e) {
            e.printStackTrace();
     }
        return rootView;
    }

    private class HospitalListRunner extends AsyncTask<String,String,String>{
        private ArrayList<HospitalDoctorsModel> hosplist;


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.hospitalDoctors+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hosplist=new ArrayList<HospitalDoctorsModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for (int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        HospitalDoctorsModel model=new HospitalDoctorsModel();
                      model.setId(object.getString("id"));
                      model.setHospitalName(object.getString("hospitalName"));
                      model.setDoctorName(object.getString("doctorName"));
                      model.setSunday(object.getString("sunday"));
                      model.setMonday(object.getString("monday"));
                      model.setTuesday(object.getString("tuesday"));
                      model.setWednesday(object.getString("wednesday"));
                      model.setThursday(object.getString("thursday"));
                      model.setFriday(object.getString("friday"));
                      model.setSaturday(object.getString("saturday"));
                      model.setMondayStartTim(object.getString("mondayStartTime"));
                      model.setMondayEndTime(object.getString("mondayEndTime"));
                      model.setPrice(object.getString("price"));
                      model.setSundayStartTime(object.getString("sundayStartTime"));
                      model.setSundayEndTime(object.getString("sundayEndTime"));
                      model.setTuesdayStartTime(object.getString("tuesdayStartTime"));
                      model.setTuesdayEndTime(object.getString("tuesdayEndTime"));
                      model.setWednesdayStartTime(object.getString("wednesdayStartTime"));
                      model.setWednesdayEndTime(object.getString("wednesdayEndTime"));
                      model.setThursdayStartTime(object.getString("thursdayStartTime"));
                      model.setThursdayEndTime(object.getString("thursdayEndTime"));
                      model.setFridayStartTime(object.getString("fridayStartTime"));
                      model.setFridayEndTime(object.getString("fridayEndTime"));
                      model.setSaturdayStartTime(object.getString("saturdayStartTime"));
                      model.setSaturdayEndTime(object.getString("saturdayEndTime"));
                      model.setDuration(object.getString("duration"));
                      model.setHospitalId(object.getString("hospitalId"));
                      model.setDoctorId(object.getString("doctorId"));
                      model.setOpPercentage(object.getString("opPercentage"));
                      model.setIpPercentage(object.getString("ipPercentage"));
                      model.setLabPercentage(object.getString("labPercentage"));
                        hosplist.add(model);
                        HospitalDoctorAdapter adapter=new HospitalDoctorAdapter(getActivity(),hosplist);
                        hsplist.setAdapter(adapter);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
