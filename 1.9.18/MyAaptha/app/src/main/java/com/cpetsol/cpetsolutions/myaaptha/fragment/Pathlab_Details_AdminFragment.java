package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.PathLabDetails_adapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PathLabDetails_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Pathlab_Details_AdminFragment extends Fragment {
    private View rootView;
  private ListView listView;
    ArrayList<PathLabDetails_Model>rowitems;

    public Pathlab_Details_AdminFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_pathlab__details__admin, container, false);
            listView=(ListView)rootView.findViewById(R.id.listview);
            AllPathLabDetails task=new AllPathLabDetails();
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    private class AllPathLabDetails extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.viewAllPathLabDetails,"GET",null);
       //     String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/viewAllPathLabDetails","GET",null);
            Log.i("Bhavani",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                rowitems = new ArrayList<>();
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        PathLabDetails_Model model=new PathLabDetails_Model();
                        model.setId(object.getString("id"));
                        model.setLabRegNo(object.getString("labRegNo"));
                        model.setLabOwnerName(object.getString("labOwnerName"));
                        model.setApprovalBy(object.getString("approvalBy"));
                        model.setLabRegYear(object.getString("labRegYear"));
                        model.setLabName(object.getString("labName"));
                        model.setQualificationYear(object.getString("qualificationYear"));
                        model.setPathLabId(object.getString("pathLabId"));
                        model.setStateCode(object.getString("stateCode"));
                        model.setDistrictCode(object.getString("districtCode"));
                        model.setDateCreated(object.getString("dateCreated"));
                        model.setViewStatus(object.getString("viewStatus"));
                        model.setStatus(object.getString("status"));
                     rowitems.add(model);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PathLabDetails_adapter adapter=new PathLabDetails_adapter(getActivity(),rowitems);
            listView.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }
}
