package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.MedicalShop_AdminModel;

import java.util.ArrayList;

/**
 * Created by Admin on 5/30/2018.
 */

public class MedicalShop_AdminAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<MedicalShop_AdminModel>list;
    private ImageView apprv,rej;
    private String stateco,distco,shpid;
private TextView shpOwnNme,shpNme,shpRegNo,ApBy,shpRegYr,ownQualYr,approved,rejected;
    public MedicalShop_AdminAdapter(Activity activity, ArrayList<MedicalShop_AdminModel> rowItems) {
        this.activity=activity;
        this.list=rowItems;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            view = inflater.inflate(R.layout.medcalshop_admin_lvrow, null);
        shpOwnNme=(TextView)view.findViewById(R.id.shpownnae);
        shpNme=(TextView)view.findViewById(R.id.shpnme);
        shpRegNo=(TextView)view.findViewById(R.id.shpregno);
        ApBy=(TextView)view.findViewById(R.id.approveby);
        shpRegYr=(TextView)view.findViewById(R.id.shpregyr);
        approved=(TextView)view.findViewById(R.id.approved);
        rejected=(TextView)view.findViewById(R.id.rejected);
        ownQualYr=(TextView)view.findViewById(R.id.ownerQualificationYr);
        apprv=(ImageView)view.findViewById(R.id.aprv);
        rej=(ImageView)view.findViewById(R.id.rej);
       final MedicalShop_AdminModel model=list.get(i);
       shpOwnNme.setText(model.getMedicalShopOwnerName());
        shpNme.setText(model.getMedicalShopName());
        shpRegNo.setText(model.getMedicalShopRegNo());
        ApBy.setText(model.getApprovedBy());
        shpRegYr.setText(model.getMedicalShopRegYear());
        ownQualYr.setText(AppHelper.ConvertJsonDateWithSec(model.getQualificationYear()));
        stateco=model.getStateCode();
        distco=model.getDistrictCode();
        shpid=model.getId().toString();
        if(model.getViewStatus().equals(1)){
            approved.setVisibility(View.VISIBLE);
        }
        if(model.getViewStatus().equals(9)){
            apprv.setVisibility(View.VISIBLE);
            rej.setVisibility(View.VISIBLE);
        }
        if(model.getViewStatus().equals(3)){
            rejected.setVisibility(View.VISIBLE);
        }
        apprv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovedTask task=new ApprovedTask();
                task.execute();
            }
        });
        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         RejectedTask task=new RejectedTask();
                task.execute();
            }
        });

        return view;
    }

    private class ApprovedTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.approveMedicalShopDetails+shpid+"&stateCode="+stateco+"&districtCode="+distco,"GET",null);
    //        String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/approveMedicalShopDetails?id="+shpid+"&stateCode="+stateco+"&districtCode="+distco,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("=====>",s);
            super.onPostExecute(s);
        }
    }

    private class RejectedTask extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall(ApisHelper.rejectMedicalShopDetails+shpid,"GET",null);
        //    String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/rejectMedicalShopDetails?id="+shpid,"GET",null);
            return Content1;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("========>",s);
            super.onPostExecute(s);
        }
    }
}
