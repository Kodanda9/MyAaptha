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
import com.cpetsol.cpetsolutions.myaaptha.adapter.MedicalShop_AdminAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.MedicalShop_AdminModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MedicalShop_AdminFragment extends Fragment {
    private View rootView;
    ListView lv;

    public MedicalShop_AdminFragment() {

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
            rootView = inflater.inflate(R.layout.fragment_medical_shop__admin, container, false);
            lv=(ListView)rootView.findViewById(R.id.admin_lv);
            AdminMedicalShopDetails admin=new AdminMedicalShopDetails();
            admin.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private class AdminMedicalShopDetails extends AsyncTask<String,String,String> {
        ArrayList<MedicalShop_AdminModel> rowItems;

        @Override
        protected String doInBackground(String... strings) {
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.viewAllMedicalShopDetails, "GET", null);
     //       String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/viewAllMedicalShopDetails", "GET", null);
            return Content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() > 0) {
                    Log.i("array:=>", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        MedicalShop_AdminModel medshopadmin=new MedicalShop_AdminModel();
                       medshopadmin.setId(jsonObject.getInt("id"));
                        medshopadmin.setMedicalShopOwnerName(jsonObject.getString("medicalShopOwnerName"));
                        medshopadmin.setMedicalShopRegNo(jsonObject.getString("medicalShopRegNo"));
                        medshopadmin.setApprovedBy(jsonObject.getString("approvedBy"));
                        medshopadmin.setMedicalShopRegYear(jsonObject.getString("medicalShopRegYear"));
                        medshopadmin.setMedicalShopName(jsonObject.getString("medicalShopName"));
                        medshopadmin.setQualificationYear(jsonObject.getString("qualificationYear"));
                        medshopadmin.setMedicalShopId(jsonObject.getString("medicalShopId"));
                        medshopadmin.setStateCode(jsonObject.getString("stateCode"));
                        medshopadmin.setDistrictCode(jsonObject.getString("districtCode"));
                        medshopadmin.setDateCreated(jsonObject.getString("dateCreated"));
                        medshopadmin.setViewStatus(jsonObject.getInt("viewStatus"));
                        rowItems.add(medshopadmin);
                    }
      MedicalShop_AdminAdapter adapter=new MedicalShop_AdminAdapter(getActivity(),rowItems);
                    lv.setAdapter(adapter);

                }
                super.onPostExecute(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
