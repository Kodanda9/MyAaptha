package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalApprovalModel;

import java.util.ArrayList;

/**
 * Created by Admin on 5/28/2018.
 */

public class HospitalApprovalAdapter extends BaseAdapter {
    private  Activity activity;
    private  ArrayList<HospitalApprovalModel> userItems;
    private LayoutInflater inflater;
   private TextView hspname,regno,regyr,regofc,location,adress,status;
    private ImageButton approve,reject;
   private String hspid;

    public HospitalApprovalAdapter(Activity activity, ArrayList<HospitalApprovalModel> rowItems) {
        this.activity=activity;
        this.userItems=rowItems;
    }


    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int i) {
        return userItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.hopspital_approval_listview, null);

        hspname=(TextView)view.findViewById(R.id.hospitalname);
        regno=(TextView)view.findViewById(R.id.regnumber);
        regyr=(TextView)view.findViewById(R.id.regyear);
        regofc=(TextView)view.findViewById(R.id.roc);
        location=(TextView)view.findViewById(R.id.location);
        adress=(TextView)view.findViewById(R.id.adress);
        status=(TextView)view.findViewById(R.id.status);
       approve=(ImageButton)view.findViewById(R.id.approve);
      reject=(ImageButton)view.findViewById(R.id.reject);
        final HospitalApprovalModel model=userItems.get(i);
        if(model.getStatus().equalsIgnoreCase("Waiting for Approval")){
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.VISIBLE);
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ApproveTask approven=new ApproveTask();
                    approven.execute();
                    approve.setVisibility(View.GONE);
                    reject.setVisibility(View.GONE);
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RejectTask rejectn=new RejectTask();
                    rejectn.execute();
                    approve.setVisibility(View.GONE);
                    reject.setVisibility(View.GONE);
                }
            });
        }else {
            approve.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);
        }

        hspname.setText(model.getHospitalName());
        regno.setText(model.getRegistrationNo());
        regyr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getRegistrationYear()));
        regofc.setText(model.getRoc());
        if(model.getLocation().equalsIgnoreCase("1")){
            location.setText("Hyderabad");
        }

        adress.setText(model.getAddress());
        status.setText(model.getStatus());
        hspid=model.getHospitalId();
        Log.i("result====>",hspid);
        return view;
    }

    private class ApproveTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {
            if(s.equalsIgnoreCase("true")){
                approve.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }else{
                approve.setVisibility(View.VISIBLE);
                reject.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.approveHospital+hspid,"GET",null);
      //      String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/approveHospital?hospitalId="+hspid,"GET",null);
            return Content;
        }
    }

    private class RejectTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall(ApisHelper.rejectHospital+hspid,"GET",null);
          //  String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/rejectHospital?hospitalId="+hspid,"GET",null);
            return Content1;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equalsIgnoreCase("true")){
                approve.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }else{
                approve.setVisibility(View.VISIBLE);
                reject.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(s);
        }
    }
}
