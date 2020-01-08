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
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.HospitalOwnerAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalOwnerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ViewHospitalOwnerFragment extends Fragment {
    private View rootView;
    private ListView HospitalOwnerLV;

    public ViewHospitalOwnerFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_view_hospital_owner, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());
           HospitalOwnerLV=(ListView)rootView.findViewById(R.id.hospitalowners);
            HospitalOwnerRunner runner=new HospitalOwnerRunner();
            runner.execute();


        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

    private class HospitalOwnerRunner extends AsyncTask<String,String,String>{
        private ArrayList<HospitalOwnerModel> arraylist;
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.viewHospitalOwner,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arraylist=new ArrayList<HospitalOwnerModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        HospitalOwnerModel model=new HospitalOwnerModel();
                          model.setName(object.getString("name"));
                          model.setEmploymentType(object.getString("employmentType"));
                          model.setHospitalName(object.getString("hospitalName"));
                          model.setHospitalId(object.getString("hospitalId"));
                        arraylist.add(model);

                    }
                    HospitalOwnerAdapter adapter=new HospitalOwnerAdapter(getActivity(),arraylist);
                    HospitalOwnerLV.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

