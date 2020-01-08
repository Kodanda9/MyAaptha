package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Pathlab_Details_AdminFragment;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PathLabDetails_Model;

import java.util.ArrayList;

/**
 * Created by Admin on 6/6/2018.
 */

public class PathLabDetails_adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private LinearLayout layout;
    private ArrayList<PathLabDetails_Model>rowItems;
    private Button reject;
    private Button approve;
    private String state,District,labid;


    public PathLabDetails_adapter(Activity activity, ArrayList<PathLabDetails_Model> rowitems) {
        this.activity=activity;
        this.rowItems=rowitems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
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
            view = inflater.inflate(R.layout.path_lab_lv_row, null);
        TextView labownname=(TextView)view.findViewById(R.id.labownname);
        TextView labname=(TextView)view.findViewById(R.id.labname);
        TextView regno=(TextView)view.findViewById(R.id.regno);
        TextView approveby=(TextView)view.findViewById(R.id.approveby);
        TextView regyr=(TextView)view.findViewById(R.id.regyr);
        TextView qualificationyr=(TextView)view.findViewById(R.id.qualificationyr);
        TextView status=(TextView)view.findViewById(R.id.status);
        approve=(Button)view.findViewById(R.id.approve);
        reject=(Button)view.findViewById(R.id.reject);
        layout=(LinearLayout)view.findViewById(R.id.approvereject);
        PathLabDetails_Model model=rowItems.get(i);
        labownname.setText(model.getLabOwnerName());
        labname.setText(model.getLabName());
        regno.setText(model.getLabRegNo());
        approveby.setText(model.getApprovalBy());
        regyr.setText(model.getLabRegYear());
        regyr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getLabRegYear()));
        qualificationyr.setText(model.getQualificationYear());
        qualificationyr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getQualificationYear()));

        state=model.getStateCode();
        District=model.getDistrictCode();
        labid=model.getId();

        status.setText(model.getStatus());
if(model.getStatus().equalsIgnoreCase("Approved")){
    layout.setVisibility(View.GONE);
}else {
    layout.setVisibility(View.VISIBLE);
}
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // approvePathLabDetails?id=1&stateCode=1&districtCode=2
                ApproveTask task=new ApproveTask(state,District,labid);
                task.execute();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
RejectTask task=new RejectTask(labid);
                task.execute();
            }
        });
        return view;
    }

    private class ApproveTask extends AsyncTask<String,String,String> {
        private String stat;
        private String disrct;
        private String labid;

        public ApproveTask(String state, String district, String labid) {
            this.stat=state;
            this.disrct=district;
            this.labid=labid;
        }


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.approvePathLabDetails+labid+"&stateCode="+stat+"&districtCode="+disrct,"GET",null);
         //   String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/approvePathLabDetails?id="+labid+"&stateCode="+stat+"&districtCode="+disrct,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Pathlab_Details_AdminFragment()).commit();


            super.onPostExecute(s);
        }
    }

    private class RejectTask extends AsyncTask<String,String,String> {

        private String labid;

        public RejectTask(String labid) {
            this.labid=labid;
        }


        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.rejectPathLabDetails+labid,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
           activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Pathlab_Details_AdminFragment()).commit();


            super.onPostExecute(s);
        }
    }
}
