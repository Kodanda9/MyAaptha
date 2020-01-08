package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.DoctorsData;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ViewHospitalsDataAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalsDataModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewHospitalsDataaFrag extends android.app.Fragment {

    private static View rootView;
    private ListView listview;
    private Button button;
    private ArrayList<HospitalsDataModel> data;



    public ViewHospitalsDataaFrag() {
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
            rootView = inflater.inflate(R.layout.view_hospitalsdata_frag, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
           listview=(ListView)rootView.findViewById(R.id.hospitalsData);
            button=(Button)rootView.findViewById(R.id.button);
            HospitalTimeSlot runner=new HospitalTimeSlot();
            runner.execute();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),DoctorsData.class);
                    startActivity(i);
                }
            });

        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

    private class HospitalTimeSlot extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.viewHospitalsData+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
         //   String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/viewHospitalsData/900000010501","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
       data=new ArrayList<HospitalsDataModel>();
            try {
                JSONArray array=new JSONArray(s);
               if(array.length()>0){
                   for(int i=0;i<array.length();i++){
                       JSONObject object=array.getJSONObject(i);
                       HospitalsDataModel model=new HospitalsDataModel();
                       model.setId(object.getString("id"));
                       model.setDuration(object.getString("duration"));
                       model.setSunday(object.getString("sunday"));
                       model.setMonday(object.getString("monday"));
                       model.setTuesday(object.getString("tuesday"));
                       model.setWednesday(object.getString("wednesday"));
                       model.setThursday(object.getString("thursday"));
                       model.setFriday(object.getString("friday"));
                       model.setSaturday(object.getString("saturday"));
                       model.setPrice(object.getString("price"));
                       JSONObject object1=object.getJSONObject("hospitals");
                       model.setHospitalName(object1.getString("hospitalName"));
                       data.add(model);
                       ViewHospitalsDataAdapter adapter=new ViewHospitalsDataAdapter(getActivity(),data);
                  listview.setAdapter(adapter);
                       adapter.notifyDataSetChanged();
                   }
               }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
