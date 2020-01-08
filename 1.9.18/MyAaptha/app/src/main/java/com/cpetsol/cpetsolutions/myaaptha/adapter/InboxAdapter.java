package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.InboxModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import java.util.ArrayList;

/**
 * Created by Admin on 6/29/2018.
 */

public class InboxAdapter extends BaseAdapter {
    private ArrayList<InboxModel> appItmList;
    private Activity activity;
    private LayoutInflater inflater;
    private String toid;


    public InboxAdapter(FragmentActivity inboxActivity, ArrayList<InboxModel> inbox) {
        this.activity=inboxActivity;
        this.appItmList=inbox;
    }

    @Override
    public int getCount() {
        return appItmList.size();
    }

    @Override
    public Object getItem(int i) {
        return appItmList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  int positiontemp=i;
        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            view=inflater.inflate(R.layout.requests_lv_row,null);
        TextView name=(TextView)view.findViewById(R.id.name);
        TextView accept=(TextView)view.findViewById(R.id.accept);
        TextView reject=(TextView)view.findViewById(R.id.reject);
        final InboxModel model = appItmList.get(i);
        Log.i("====>",model.toString());
        name.setText(model.getFullName());
        toid=model.getId();
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptRunner runner=new AcceptRunner();
                runner.execute();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectRunner runner=new RejectRunner();
                runner.execute();
            }
        });
        return view;
    }

    private class AcceptRunner extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.confirmRequest+ SessinSave.getsessin("profile_id",activity)+"?id="+toid,"GET",null);
          //  String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/userRest/confirmRequest/900000010501?id=900000030501","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("result===>",s);
            
            super.onPostExecute(s);
        }
    }

    private class RejectRunner extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.deleteRequest+ SessinSave.getsessin("profile_id",activity)+"?id="+toid,"GET",null);
      //      String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/userRest/deleteRequest/900000010501?id=900000030501","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("result===>",s);

            super.onPostExecute(s);
        }
    }
}
