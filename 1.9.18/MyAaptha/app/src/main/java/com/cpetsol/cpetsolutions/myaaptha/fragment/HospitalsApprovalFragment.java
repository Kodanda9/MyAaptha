package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.HospitalApprovalAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalApprovalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalsApprovalFragment extends Fragment {
    private ListView hospitallistview;
    private View rootView;
    private ImageButton approve,reject;


    public HospitalsApprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }//if
        try {
            rootView = inflater.inflate(R.layout.hospitals_approval_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());
            hospitallistview = (ListView) rootView.findViewById(R.id.listview);
            approve=(ImageButton)rootView.findViewById(R.id.approve);
            reject=(ImageButton)rootView.findViewById(R.id.reject);
            AsyncTaskRunner1 runner = new AsyncTaskRunner1();
            runner.execute();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }//onCreateView


    private class AsyncTaskRunner1 extends AsyncTask<String, String, String> {
        private ArrayList<HospitalApprovalModel> rowItems;

        @Override
        protected String doInBackground(String... strings) {
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.hospitalsApproval, "GET", null);
       //     String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/hospitalsApproval", "GET", null);
            Log.i("data=====>", Content);

            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems = new ArrayList<HospitalApprovalModel>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() > 0) {
                    Log.i("array:=>", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HospitalApprovalModel item = new HospitalApprovalModel();
                        item.setHospitalName(jsonObject.getString("hospitalName"));
                        item.setRegistrationNo(jsonObject.getString("registrationNo"));
                        item.setRegistrationYear(jsonObject.getString("registrationYear"));
                        item.setRoc(jsonObject.getString("roc"));
                        item.setLocation(jsonObject.getString("location"));
                        item.setAddress(jsonObject.getString("address"));
                        item.setStatus(jsonObject.getString("status"));
                        item.setHospitalId(jsonObject.getString("hospitalId"));


                        rowItems.add(item);
                    }

                }

                super.onPostExecute(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HospitalApprovalAdapter hospadapter=new HospitalApprovalAdapter(getActivity(),rowItems);
            hospitallistview.setAdapter(hospadapter);
            hospadapter.notifyDataSetChanged();
        }
    }
}
